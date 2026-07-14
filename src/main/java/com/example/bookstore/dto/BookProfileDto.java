package com.example.bookstore.dto;

/** Extended book detail (one-to-one), mirroring the existing BookProfile fields. */
public record BookProfileDto(String genre, Integer pages, String language) {
}
