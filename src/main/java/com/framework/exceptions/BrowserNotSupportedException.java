package com.framework.exceptions;

/**
 * Exception thrown when browser initialization fails
 */
public class BrowserNotSupportedException extends RuntimeException {

    public BrowserNotSupportedException(String message) {
        super(message);
    }

    public BrowserNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}

