package com.example.bookstore.dto;

/** Result of creating a book and author together: both full views, already linked. */
public record BookWithAuthorResponse(BookResponse book, AuthorResponse author) {
}
