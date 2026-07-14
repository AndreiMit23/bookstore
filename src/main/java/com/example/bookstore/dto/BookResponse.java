package com.example.bookstore.dto;

import java.util.List;

/** Full book view: publisher (many-to-one), authors (many-to-many), profile (one-to-one). */
public record BookResponse(
        Long id,
        String title,
        String description,
        Integer publicationYear,
        PublisherSummary publisher,
        List<AuthorSummary> authors,
        BookProfileDto profile) {
}
