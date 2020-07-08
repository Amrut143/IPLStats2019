package com.bridgelabz.iplanalyser.service;

import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.MostRunsCSV;
import com.bridgelabz.iplanalyser.model.MostWktsCSV;
import com.bridgelabz.opencsvbuilder.exceptions.CSVBuilderException;
import com.bridgelabz.opencsvbuilder.service.CSVBuilderFactory;
import com.bridgelabz.opencsvbuilder.service.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class IPLAnalyser {

    /**
     *
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    public int loadIPLMostRunsData(String csvFilePath) throws IPLAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<MostRunsCSV> battingDataIterator = csvBuilder.getCSVFileIterator(reader, MostRunsCSV.class);
            Iterable<MostRunsCSV> battingDataIterable = () -> battingDataIterator;
            return (int) StreamSupport.stream(battingDataIterable.spliterator(), false).count();
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
            Iterable<MostWktsCSV> bowlingDataIterable = () -> bowlingDataIterator;
            return (int) StreamSupport.stream(bowlingDataIterable.spliterator(), false).count();
        } catch (IOException | CSVBuilderException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, "there is some issue related to csv file");
        } catch (RuntimeException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, "might be issue in delimiter or header");
        }
    }
}
