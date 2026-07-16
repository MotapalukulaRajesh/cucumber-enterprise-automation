package com.framework.exceptions;

/**
 * Exception thrown when a required locator is not found in the object repository
 */
public class LocatorNotFoundException extends RuntimeException {

    public LocatorNotFoundException(String message) {
        super(message);
    }

    public LocatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

