package com.example.bookstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Create/update payload for a book. Relationships are referenced by id: {@code publisherId}
 * (many-to-one) and {@code authorIds} (many-to-many). Optional {@code profile} cascades into
 * the one-to-one BookProfile.
 */
public record BookRequest(
        @NotBlank String title,
        String description,
        Integer publicationYear,
        Long publisherId,
        Set<Long> authorIds,
        @Valid BookProfileDto profile) {
}
