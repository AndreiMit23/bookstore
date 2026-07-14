package com.example.bookstore.dto;

/** Compact author reference used inside book responses. */
public record AuthorSummary(Long id, String firstName, String lastName) {
}
