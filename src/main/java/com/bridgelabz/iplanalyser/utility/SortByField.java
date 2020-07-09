package com.bridgelabz.iplanalyser.utility;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    static Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    public enum Parameter {
        BATTING_AVG, STRIKERATE, SIX_AND_FOURS, SIX_AND_FOURS_WITH_STRIKERATE, BAT_AVG_WITH_STRIKERATE;
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

        sortParameterComparator.put(Parameter.BATTING_AVG, batAvgComparator);
        sortParameterComparator.put(Parameter.STRIKERATE, strikeRateComparator);
        sortParameterComparator.put(Parameter.SIX_AND_FOURS, sixesAndFoursComparator);
        sortParameterComparator.put(Parameter.SIX_AND_FOURS_WITH_STRIKERATE,
                                    sixesAndFoursComparator.thenComparing(strikeRateComparator));
        sortParameterComparator.put(Parameter.BAT_AVG_WITH_STRIKERATE,
                                    batAvgComparator.thenComparing(strikeRateComparator));

        Comparator<IPLRecordDAO> comparator = sortParameterComparator.get(parameter);
        return comparator;
    }
}