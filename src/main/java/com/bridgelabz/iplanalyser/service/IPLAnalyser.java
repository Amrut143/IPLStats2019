package com.bridgelabz.iplanalyser.service;

import com.bridgelabz.iplanalyser.adapter.IPLAdapter;
import com.bridgelabz.iplanalyser.adapter.IPLAdapterFactory;
import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.utility.PlayerType;
import com.bridgelabz.iplanalyser.utility.SortByField;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;


public class IPLAnalyser {

    Map<String, IPLRecordDAO> runCSVMap;
    public PlayerType playerType;

    public IPLAnalyser(PlayerType playerType) {
        this.playerType = playerType;
    }

    public IPLAnalyser() {
        this.runCSVMap = new HashMap<>();
    }

    public <T> int loadIPLData(String... csvFilePath) throws IPLAnalyserException {
        runCSVMap = new IPLAdapterFactory().getPlayerData(playerType, csvFilePath);
        return runCSVMap.size();
    }

    /**
     * Function to sort ipl data parameter wise
     * @param parameter
     * @return
     * @throws IPLAnalyserException
     */
    public String getFieldWiseSortedIPLPLayersRecords(SortByField.Parameter parameter) throws IPLAnalyserException {
        if (runCSVMap == null || runCSVMap.size() == 0) {
            throw new IPLAnalyserException(IPLAnalyserException.ExceptionType.NO_IPL_DATA, "NO_IPL_DATA");
        }

        Comparator<IPLRecordDAO> iplComparator = SortByField.getParameter(parameter);
       ArrayList iplDAOList = runCSVMap.values().stream()
                                        .sorted(iplComparator)
                                        .collect(Collectors.toCollection(ArrayList::new));
        String sortedIplData = new Gson().toJson(iplDAOList);
        return sortedIplData;
    }
}
