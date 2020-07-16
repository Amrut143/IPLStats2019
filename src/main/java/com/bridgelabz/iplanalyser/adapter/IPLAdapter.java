package com.bridgelabz.iplanalyser.adapter;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;
import com.bridgelabz.iplanalyser.model.IPLBowlerDataCSV;
import com.bridgelabz.iplanalyser.utility.PlayerType;
import com.bridgelabz.opencsvbuilder.exceptions.CSVBuilderException;
import com.bridgelabz.opencsvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.opencsvbuilder.service.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class IPLAdapter {

    /**
     *
     * @param playerType
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    public abstract Map<String, IPLRecordDAO> loadIPLData(PlayerType playerType, String... csvFilePath) throws IPLAnalyserException;

    /**
     *
     * @param iplCSVClass
     * @param csvFilePath
     * @param <T>
     * @return
     * @throws IPLAnalyserException
     */
    public <T> Map<String, IPLRecordDAO> loadIPLData(Class<T> iplCSVClass, String... csvFilePath) throws IPLAnalyserException {
        Map<String, IPLRecordDAO> iplRecordDAOMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator mostRunCSVIterator = csvBuilder.getCSVFileIterator(reader, iplCSVClass);
            Iterable<T> mostRunCSVIterable = () -> mostRunCSVIterator;
            String className = iplCSVClass.getSimpleName();
            switch (className) {
                case "IPLBatsmanDataCSV":
                    StreamSupport.stream(mostRunCSVIterable.spliterator(), false)
                            .map(IPLBatsmanDataCSV.class::cast)
                            .forEach(mostRunsCSV -> iplRecordDAOMap.put(mostRunsCSV.player, new IPLRecordDAO(mostRunsCSV)));
                case "IPLBowlerDataCSV":
                    StreamSupport.stream(mostRunCSVIterable.spliterator(), false)
                            .map(IPLBowlerDataCSV.class::cast)
                            .filter(mostWktsCSV -> mostWktsCSV.average != 0.0)
                            .forEach(mostWktsCSV -> iplRecordDAOMap.put(mostWktsCSV.player, new IPLRecordDAO(mostWktsCSV)));
            }
            return iplRecordDAOMap;
        } catch (IOException | CSVBuilderException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, e.getMessage());
        } catch (RuntimeException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.getMessage());
        }
    }

    private void prepareFile(String filePath, String replaceWrongValuesWith) throws IOException {
        String searchFor = "-";
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            List<String> replaced = lines
                    .map(line-> line.replaceAll(searchFor, replaceWrongValuesWith))
                    .collect(Collectors.toList());
            Files.write(Paths.get("./src/test/resources/readableCsv.csv"), replaced);
        } catch (NoSuchFileException e) {
            throw e;
        }
    }
}
