package com.example.bookstore.dto;

/** Open Library cover image URLs in the three sizes it offers. Null when a book has no cover. */
public record CoverImages(String small, String medium, String large) {
}
