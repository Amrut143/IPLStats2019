package com.bridgelabz.iplanalyser.utility;

import com.bridgelabz.iplanalyser.dao.IPLRecordDAO;
import com.bridgelabz.iplanalyser.model.IPLBatsmanDataCSV;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    static Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    public enum Parameter {
        BATTING_AVG;
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

        sortParameterComparator.put(Parameter.BATTING_AVG, avgComparator);

        Comparator<IPLRecordDAO> comparator = sortParameterComparator.get(parameter);
        return comparator;
    }
}