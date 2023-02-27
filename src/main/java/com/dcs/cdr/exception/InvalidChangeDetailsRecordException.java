package com.dcs.cdr.exception;

public class InvalidChangeDetailsRecordException extends RuntimeException {
    public InvalidChangeDetailsRecordException(String message) {
        // We can use advice
        super(message);
    }
}
