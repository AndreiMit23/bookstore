package com.example.bookstore.dto;

import java.util.List;

/** Detail for a single book looked up by ISBN on the Open Library public API. */
public record ExternalBookDetail(String title, List<String> authors, List<String> publishers,
        Integer numberOfPages, String publishDate, String isbn, CoverImages cover) {
}
