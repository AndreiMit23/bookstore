package com.example.bookstore.dto;

/** Compact book reference used inside author/publisher responses. */
public record BookSummary(Long id, String title) {
}
