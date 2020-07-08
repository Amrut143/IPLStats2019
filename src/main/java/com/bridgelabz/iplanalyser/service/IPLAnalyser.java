package com.bridgelabz.iplanalyser.service;

import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.MostRunCSV;
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
    public int loadIPLMostRunsData(String csvFilePath) throws IPLAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<MostRunCSV> battingDataIterator = csvBuilder.getCSVFileIterator(reader, MostRunCSV.class);
            Iterable<MostRunCSV> battingDataIterable = () -> battingDataIterator;
            return (int) StreamSupport.stream(battingDataIterable.spliterator(), false).count();
        } catch (IOException | CSVBuilderException e) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, e.getMessage());
        }
    }
}
