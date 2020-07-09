package com.bridgelabz.iplanalyser.exception;

public class IPLAnalyserException extends Exception {
    public enum ExceptionType {
        IPL_FILE_PROBLEM,
        CSV_FILE_INTERNAL_ISSUE, NO_IPL_DATA;
    }
    public ExceptionType type;

    /**
     *
     * @param message
     * @param type
     */
    public IPLAnalyserException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
