package com.bridgelabz.iplanalyser.utility;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    static Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    public enum Parameter {
        BATTING_AVG, STRIKERATE, SIX_AND_FOURS, SIX_AND_FOURS_WITH_STRIKERATE, BAT_AVG_WITH_STRIKERATE, BAT_RUN_WITH_AVG,
        BOWLING_AVG, BOWL_STRIKERATE, ECONOMY, FIVEWKT_FOURWKT_STRIKERATE, BOWL_AVG_WITH_STRIKERATE, BOWL_WKTS_WITH_AVG;
    }

    SortByField() {
    }

    /**
     * Function to get field for sorting
     * @param parameter
     * @return
     */
    public static Comparator getParameter(SortByField.Parameter parameter) {
        Comparator<IPLRecordDAO> batAvgComparator = Comparator.comparing(mostRunCSV -> mostRunCSV.battingAverage);
        Comparator<IPLRecordDAO> strikeRateComparator = Comparator.comparing(mostRunCSV -> mostRunCSV.strikeRate);
        Comparator<IPLRecordDAO> sixesAndFoursComparator =
                Comparator.comparing(iplBatsmanDAO -> (iplBatsmanDAO.six * 6) + (iplBatsmanDAO.fours * 4),
                                     Comparator.reverseOrder());
        Comparator<IPLRecordDAO> batRunComparator = Comparator.comparing(mostRunCSV -> mostRunCSV.batsmanRun);
        Comparator<IPLRecordDAO> bowlAvgComparator = Comparator.comparing(mostWktsCSV -> mostWktsCSV.bowlingAverage);
        Comparator<IPLRecordDAO> econComparator = Comparator.comparing(mostWktsCSV -> mostWktsCSV.economy);
        Comparator<IPLRecordDAO> bowlingSRWith4n5W = Comparator.comparing(iplRecordDAO -> (
                                              (iplRecordDAO.fourWkts * 4) + (iplRecordDAO.fiveWkts * 5)), Comparator.reverseOrder());
        Comparator<IPLRecordDAO> wktsComparator = Comparator.comparing(mostWktsCSV -> mostWktsCSV.wkts);

        sortParameterComparator.put(Parameter.BATTING_AVG, batAvgComparator);
        sortParameterComparator.put(Parameter.STRIKERATE, strikeRateComparator);
        sortParameterComparator.put(Parameter.SIX_AND_FOURS, sixesAndFoursComparator);
        sortParameterComparator.put(Parameter.SIX_AND_FOURS_WITH_STRIKERATE,
                                    sixesAndFoursComparator.thenComparing(strikeRateComparator));
        sortParameterComparator.put(Parameter.BAT_AVG_WITH_STRIKERATE,
                                    batAvgComparator.thenComparing(strikeRateComparator));
        sortParameterComparator.put(Parameter.BAT_RUN_WITH_AVG,
                                    batRunComparator.thenComparing(batAvgComparator));
        sortParameterComparator.put(Parameter.BOWLING_AVG, bowlAvgComparator);
        sortParameterComparator.put(Parameter.BOWL_STRIKERATE, strikeRateComparator);
        sortParameterComparator.put(Parameter.ECONOMY, econComparator);
        sortParameterComparator.put(Parameter.FIVEWKT_FOURWKT_STRIKERATE,
                                    bowlingSRWith4n5W.thenComparing(strikeRateComparator));
        sortParameterComparator.put(Parameter.BOWL_AVG_WITH_STRIKERATE,
                                    bowlAvgComparator.thenComparing(strikeRateComparator));
        sortParameterComparator.put(Parameter.BOWL_WKTS_WITH_AVG,
                                    wktsComparator.thenComparing(bowlAvgComparator));


        Comparator<IPLRecordDAO> comparator = sortParameterComparator.get(parameter);
        return comparator;
    }
}