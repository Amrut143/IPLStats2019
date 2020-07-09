package com.bridgelabz.iplanalysertest;

import com.bridgelabz.iplanalyser.adapter.BatsmanAdapter;
import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;
import com.bridgelabz.iplanalyser.service.IPLAnalyser;
import com.bridgelabz.iplanalyser.utility.PlayerType;
import com.bridgelabz.iplanalyser.utility.SortByField;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class IPLAnalyserTest {
    private static final String IPL_MOST_RUNS_CSV_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private static final String WRONG_MOST_RUNS_CSV_FILE_PATH = "./src/main/resources/IPL2019FactsheetMostRuns.csv";
    private static final String IPL_MOST_WKTS_CSV_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";
    private static final String WRONG_MOST_WKTS_CSV_FILE_PATH = "./src/main/resources/IPL2019FactsheetMostWkts.csv";



    @Test
    public void givenIPLMostRunsCSVFile_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            int numOfRecords = iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(100, numOfRecords);
        } catch (IPLAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongIPLMostRunsCSVFile_ShouldReturnCustomExceptionType() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(WRONG_MOST_RUNS_CSV_FILE_PATH);
        } catch (IPLAnalyserException e) {
            Assert.assertEquals(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsCSVFile_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
        } catch (IPLAnalyserException e) {
            Assert.assertEquals(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsCSVFile_WhenCorrect_ButHeaderIncorrect_ShouldThrowException() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
        } catch (IPLAnalyserException e) {
            Assert.assertEquals(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostWktsCSVFile_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BOWLER);
            int numOfRecords = iplAnalyser.loadIPLData(IPL_MOST_WKTS_CSV_FILE_PATH);
            Assert.assertEquals(99, numOfRecords);
        } catch (IPLAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongIPLMostWktsCSVFile_ShouldReturnCustomExceptionType() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BOWLER);
            iplAnalyser.loadIPLData(WRONG_MOST_WKTS_CSV_FILE_PATH);
        } catch (IPLAnalyserException e) {
            Assert.assertEquals(IPLAnalyserException.ExceptionType.IPL_FILE_PROBLEM, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostWktsCSVFile_WhenCorrect_ButDelimiterIncorrect_ShouldThrowException() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BOWLER);
            iplAnalyser.loadIPLData(IPL_MOST_WKTS_CSV_FILE_PATH);
        } catch (IPLAnalyserException e) {
            Assert.assertEquals(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostWktsCSVFile_WhenCorrect_ButHeaderIncorrect_ShouldThrowException() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BOWLER);
            iplAnalyser.loadIPLData(IPL_MOST_WKTS_CSV_FILE_PATH);
        } catch (IPLAnalyserException e) {
            Assert.assertEquals(IPLAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUE, e.type);
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsCSVFile_WhenSortedOnAvg_ShouldReturnCorrectDesiredSortedData() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
            String iplPLayersRecords = iplAnalyser.getFieldWiseSortedIPLPLayersRecords(SortByField.Parameter.BATTING_AVG);
            IPLBatsmanDataCSV[] mostRunsCSV = new Gson().fromJson(iplPLayersRecords, IPLBatsmanDataCSV[].class);
            Assert.assertEquals("MS Dhoni", mostRunsCSV[mostRunsCSV.length - 1].player);
        } catch (IPLAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsCSVFile_WhenSortedOnStrikeRate_ShouldReturnCorrectDesiredSortedData() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
            String iplPlayersRecords = iplAnalyser.getFieldWiseSortedIPLPLayersRecords(SortByField.Parameter.STRIKERATE);
            IPLBatsmanDataCSV[] mostRunsCSV = new Gson().fromJson(iplPlayersRecords, IPLBatsmanDataCSV[].class);
            Assert.assertEquals("Ishant Sharma", mostRunsCSV[mostRunsCSV.length - 1].player);
        } catch (IPLAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMOstRunsCSVFile_WhenSortedOn4SAnd6s_ShouldReturnCorrectDesiredSortedData() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
            String iplPLayersRecords = iplAnalyser.getFieldWiseSortedIPLPLayersRecords(SortByField.Parameter.SIX_AND_FOURS);
            IPLBatsmanDataCSV[] mostRunsCSV = new Gson().fromJson(iplPLayersRecords, IPLBatsmanDataCSV[].class);
            Assert.assertEquals("Andre Russell", mostRunsCSV[0].player);
        } catch (IPLAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMOstRunsCSVFile_WhenSortedOn4SAnd6sWithStrikeRate_ShouldReturnCorrectDesiredSortedData() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerType.BATSMAN);
            iplAnalyser.loadIPLData(IPL_MOST_RUNS_CSV_FILE_PATH);
            String iplPLayersRecords = iplAnalyser.getFieldWiseSortedIPLPLayersRecords(SortByField.Parameter.SIX_AND_FOURS_WITH_STRIKERATE);
            IPLBatsmanDataCSV[] mostRunCSVS = new Gson().fromJson(iplPLayersRecords, IPLBatsmanDataCSV[].class);
            Assert.assertEquals("Andre Russell", mostRunCSVS[0].player);
        } catch (IPLAnalyserException e) {
            e.printStackTrace();
        }
    }
}
