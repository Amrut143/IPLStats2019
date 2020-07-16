package com.bridgelabz.iplanalyser.dao;

import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;
import com.bridgelabz.iplanalyser.model.IPLBowlerDataCSV;

public class IPLRecordDAO {

    public String player;
    public int batsmanRun;
    public int bowlerRun;
    public double strikeRate;
    public double battingAverage;
    public double bowlingAverage;
    public int fours;
    public int six;
    public int wkts;
    public int fourWkts;
    public int fiveWkts;
    public double economy;
    public int match;
    public double over;

    /**
     *
     * @param iplBatsmanDataCSV
     */
    public IPLRecordDAO(IPLBatsmanDataCSV iplBatsmanDataCSV) {
        player = iplBatsmanDataCSV.player;
        batsmanRun = iplBatsmanDataCSV.run;
        strikeRate = iplBatsmanDataCSV.strikeRate;
        battingAverage = iplBatsmanDataCSV.avg;
        fours = iplBatsmanDataCSV.fours;
        six = iplBatsmanDataCSV.six;
        match = iplBatsmanDataCSV.match;
    }

    /**
     *
     * @param iplBowlerDataCSV
     */
    public IPLRecordDAO(IPLBowlerDataCSV iplBowlerDataCSV) {
        player = iplBowlerDataCSV.player;
        bowlerRun = iplBowlerDataCSV.runs;
        strikeRate = iplBowlerDataCSV.strikeRate;
        bowlingAverage = iplBowlerDataCSV.average;
        wkts = iplBowlerDataCSV.wkts;
        fourWkts = iplBowlerDataCSV.fourWkts;
        fiveWkts = iplBowlerDataCSV.fiveWkts;
        economy = iplBowlerDataCSV.economy;
        match = iplBowlerDataCSV.match;
        over = iplBowlerDataCSV.over;
    }
}
