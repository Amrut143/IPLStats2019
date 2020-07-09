package com.bridgelabz.iplanalyser.adapter;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.exception.IPLAnalyserException;
import com.bridgelabz.iplanalyser.service.IPLAnalyser;
import com.bridgelabz.iplanalyser.utility.PlayerType;

import java.util.Map;

public class IPLAdapterFactory {

    /**
     *
     * @param playerType
     * @param csvFilePath
     * @return
     * @throws IPLAnalyserException
     */
    public Map<String, IPLRecordDAO> getPlayerData(PlayerType playerType, String... csvFilePath) throws IPLAnalyserException {
        if (playerType.equals(playerType.BATSMAN))
            return new BatsmanAdapter().loadIPLData(playerType, csvFilePath);
        else if (playerType.equals(playerType.BOWLER))
            return new BowlerAdapter().loadIPLData(playerType, csvFilePath);
        return null;
    }
}
