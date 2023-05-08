package com.vsu.app.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
