package com.example.bookstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/** Create/update payload for an author; optional profile cascades into the 1:1. */
public record AuthorRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Valid AuthorProfileDto profile) {
}
