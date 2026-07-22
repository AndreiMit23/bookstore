package com.example.bookstore.exception;

/** Thrown by services when a requested entity id does not exist. Maps to HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id " + id + " not found");
    }

    public ResourceNotFoundException(String resource, String identifier) {
        super(resource + " '" + identifier + "' not found");
    }
}
