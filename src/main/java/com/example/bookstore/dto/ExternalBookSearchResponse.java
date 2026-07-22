package com.example.bookstore.dto;

import java.util.List;

/** Wrapper for an Open Library search: total matches plus the mapped page of results. */
public record ExternalBookSearchResponse(int numFound, List<ExternalBookSummary> results) {
}
