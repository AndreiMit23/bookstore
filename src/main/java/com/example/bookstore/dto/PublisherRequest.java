package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;

/** Create/update payload for a publisher. */
public record PublisherRequest(@NotBlank String name, String city) {
}
