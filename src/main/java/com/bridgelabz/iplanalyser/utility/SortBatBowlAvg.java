package com.bridgelabz.iplanalyser.utility;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;

import java.util.Comparator;

public class SortBatBowlAvg implements Comparator<IPLRecordDAO> {

    /**
     *
     * @param obj1
     * @param obj2
     * @return
     */
    @Override
    public int compare(IPLRecordDAO obj1, IPLRecordDAO obj2) {
        return (int) (obj1.battingAverage - (1d/obj1.bowlingAverage) - (obj2.battingAverage - (1d/obj2.bowlingAverage)));
    }
}
