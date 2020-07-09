package com.bridgelabz.iplanalyser.service;

import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.MostRunsCSV;
import com.bridgelabz.iplanalyser.model.MostWktsCSV;
import com.bridgelabz.opencsvbuilder.exceptions.CSVBuilderException;
import com.bridgelabz.opencsvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.opencsvbuilder.service.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class IPLAnalyser {

    List<MostRunsCSV> iplCSVList;
    /**
     *
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    public int loadIPLMostRunsData(String csvFilePath) throws IPLAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            iplCSVList = csvBuilder.getCSVFileList(reader, MostRunsCSV.class);
            return iplCSVList.size();
        } catch (IOException | CSVBuilderException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, "there is some issue related to csv file");
        } catch (RuntimeException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be issue in delimiter or header");
        }
    }

    /**
     *
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    public int loadIPLMostWktsData(String csvFilePath) throws IPLAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<MostWktsCSV> bowlingDataIterator = csvBuilder.getCSVFileIterator(reader, MostWktsCSV.class);
            return this.getCount(bowlingDataIterator);
        } catch (IOException | CSVBuilderException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, "there is some issue related to csv file");
        } catch (RuntimeException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be issue in delimiter or header");
        }
    }

    /**
     * Function to count the number of entries
     * @param iterator
     * @param <T>
     * @return
     */
    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> csvIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numberOfEntries;
    }

    public String getAvgWiseSortedIPLPLayersRecords(String csvFilePath) throws IPLAnalyserException {
        loadIPLMostRunsData(csvFilePath);
        if (iplCSVList == null || iplCSVList.size() == 0) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.NO_IPL_DATA, "NO_IPL_DATA");
        }

        Comparator<MostRunsCSV> iplComparator = Comparator.comparing(iplRecord -> iplRecord.avg);
        iplCSVList.sort(iplComparator);
        String sortedIplData = new Gson().toJson(iplCSVList);
        return sortedIplData;

    }
}
