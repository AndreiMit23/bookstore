package com.example.bookstore.dto;

/** Author biographical detail (one-to-one), used in both requests and responses. */
public record AuthorProfileDto(String biography, String website) {
}
