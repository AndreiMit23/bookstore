package com.example.bookstore.module_author_book;

import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_book.BookResponse;

import java.util.List;

public class AuthorAndBookResponse {
    private final List<AuthorResponse> authorResponseList;
    private final List<BookResponse> bookResponseList;

    public AuthorAndBookResponse(List<AuthorResponse> authorResponseList, List<BookResponse> bookResponseList) {
        this.authorResponseList = authorResponseList;
        this.bookResponseList = bookResponseList;
    }

    public List<AuthorResponse> getAuthorResponseList() {
        return authorResponseList;
    }

    public List<BookResponse> getBookResponseList() {
        return bookResponseList;
    }
}
