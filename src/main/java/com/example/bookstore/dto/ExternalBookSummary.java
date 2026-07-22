package com.example.bookstore.dto;

import java.util.List;

/** One search hit from the Open Library public API. */
public record ExternalBookSummary(String title, List<String> authors, Integer firstPublishYear, String isbn,
        CoverImages cover) {
}
