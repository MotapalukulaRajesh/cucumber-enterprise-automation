package com.framework.exceptions;

/**
 * Exception thrown when explicit wait times out
 */
public class WaitTimeoutException extends RuntimeException {

    public WaitTimeoutException(String message) {
        super(message);
    }

    public WaitTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}

