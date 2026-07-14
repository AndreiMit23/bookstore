package com.example.bookstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** Payload for creating a new author and a new book together, then linking them (M:N). */
public record BookWithAuthorRequest(
        @NotNull @Valid AuthorRequest author,
        @NotNull @Valid BookRequest book) {
}
