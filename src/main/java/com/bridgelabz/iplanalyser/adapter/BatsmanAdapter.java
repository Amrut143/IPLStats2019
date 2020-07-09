package com.bridgelabz.iplanalyser.adapter;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;
import com.bridgelabz.iplanalyser.model.IPLBowlerDataCSV;
import com.bridgelabz.iplanalyser.service.IPLAnalyser;
import com.bridgelabz.iplanalyser.utility.PlayerType;
import com.bridgelabz.opencsvbuilder.exceptions.CSVBuilderException;
import com.bridgelabz.opencsvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.opencsvbuilder.service.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class BatsmanAdapter extends IPLAdapter {

    /**
     *
     * @param playerType
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    @Override
    public Map<String, IPLRecordDAO> loadIPLData(PlayerType playerType, String... csvFilePath) throws IPLAnalyserException {
        Map<String, IPLRecordDAO>  recordDAOMap = super.loadIPLData(IPLBatsmanDataCSV.class, csvFilePath[0]);
        if(csvFilePath.length == 2)
            this.loadMostWKTSCSV(recordDAOMap, csvFilePath[1]);
        return recordDAOMap;
    }

    /**
     *
     * @param recordDAOMap
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    private int loadMostWKTSCSV(Map<String, IPLRecordDAO> recordDAOMap, String csvFilePath) throws IPLAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLBowlerDataCSV> wktsCSVIterator = csvBuilder.getCSVFileIterator(reader, IPLBowlerDataCSV.class);
            Iterable<IPLBowlerDataCSV> wktsCSVS = () -> wktsCSVIterator;
            StreamSupport.stream(wktsCSVS.spliterator(), false)
                    .filter(csvIPL -> recordDAOMap.get(csvIPL.player) != null)
                    .forEach(mostWktsCSV -> {
                        recordDAOMap.get(mostWktsCSV.player).bowlingAverage = mostWktsCSV.average;
                        recordDAOMap.get(mostWktsCSV.player).wkts = mostWktsCSV.wkts;
                    });
            return recordDAOMap.size();
        } catch (IOException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, e.getMessage());
        } catch (RuntimeException | CSVBuilderException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.getMessage());
        }
    }
}
