package com.example.bookstore.module_author_book;

import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_book.BookResponse;

import java.util.List;

public class AuthorAndBookResponse {
    private final List<AuthorResponse> authors;
    private final List<BookResponse> books;
    public AuthorAndBookResponse(List<AuthorResponse> authors, List<BookResponse> books) {
        this.authors = authors;
        this.books = books;
    }

    public List<AuthorResponse> getAuthors() {
        return authors;
    }

    public List<BookResponse> getBooks() {
        return books;
    }
}
