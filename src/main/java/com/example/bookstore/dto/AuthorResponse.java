package com.example.bookstore.dto;

import java.util.List;

/** Full author view: profile (1:1) and books (many-to-many). */
public record AuthorResponse(
        Long id,
        String firstName,
        String lastName,
        AuthorProfileDto profile,
        List<BookSummary> books) {
}
