package com.bridgelabz.iplanalyser.utility;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    static Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    public enum Parameter {
        BATTING_AVG, STRIKERATE, SIX_AND_FOURS;
    }

    SortByField() {
    }

    /**
     * Function to get field for sorting
     * @param parameter
     * @return
     */
    public static Comparator getParameter(SortByField.Parameter parameter) {
        Comparator<IPLRecordDAO> avgComparator = Comparator.comparing(mostRunCSV -> mostRunCSV.battingAverage);
        Comparator<IPLRecordDAO> strikeRateComparator = Comparator.comparing(mostRunCSV -> mostRunCSV.strikeRate);
        Comparator<IPLRecordDAO> sixesAndFoursComparator =
                Comparator.comparing(iplBatsmanDAO -> (iplBatsmanDAO.six * 6) + (iplBatsmanDAO.fours * 4),
                                     Comparator.reverseOrder());

        sortParameterComparator.put(Parameter.BATTING_AVG, avgComparator);
        sortParameterComparator.put(Parameter.STRIKERATE, strikeRateComparator);
        sortParameterComparator.put(Parameter.SIX_AND_FOURS, sixesAndFoursComparator);

        Comparator<IPLRecordDAO> comparator = sortParameterComparator.get(parameter);
        return comparator;
    }
}