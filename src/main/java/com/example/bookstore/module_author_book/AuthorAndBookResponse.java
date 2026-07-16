package com.example.bookstore.module_author_book;

import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_book.BookResponse;

import java.util.List;

public class AuthorAndBookResponse {
    private final List<AuthorResponse> authors;

    public AuthorAndBookResponse(List<AuthorResponse> authors) {
        this.authors = authors;
    }

    public List<AuthorResponse> getAuthors() {
        return authors;
    }
}
