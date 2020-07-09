package com.bridgelabz.iplanalyser.adapter;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.model.IPLBowlerDataCSV;
import com.bridgelabz.iplanalyser.utility.PlayerType;

import java.util.Map;

public class BowlerAdapter extends IPLAdapter {

    /**
     *
     * @param playerType
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    @Override
    public Map<String, IPLRecordDAO> loadIPLData(PlayerType playerType, String... csvFilePath) throws IPLAnalyserException {
        Map<String, IPLRecordDAO> recordDAOMap = super.loadIPLData(IPLBowlerDataCSV.class, csvFilePath);
        return recordDAOMap;
    }
}
