package com.example.bookstore.exception;

/** Thrown when a downstream public API (e.g. Open Library) fails or is unreachable. Maps to HTTP 502. */
public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
