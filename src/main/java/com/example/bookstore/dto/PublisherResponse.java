package com.example.bookstore.dto;

import java.util.List;

/** Full publisher view, including its books (one-to-many). */
public record PublisherResponse(
        Long id,
        String name,
        String city,
        List<BookSummary> books) {
}
