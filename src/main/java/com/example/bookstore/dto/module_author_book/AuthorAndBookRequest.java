package com.example.bookstore.dto.module_author_book;

import com.example.bookstore.dto.module_author.AuthorRequest;
import com.example.bookstore.dto.module_book.BookRequest;

public class AuthorAndBookRequest {
    private final AuthorRequest authorRequest;
    private final BookRequest bookRequest;

    public AuthorAndBookRequest(AuthorRequest authorRequest, BookRequest bookRequest){
        this.authorRequest = authorRequest;
        this.bookRequest = bookRequest;
    }

    public AuthorRequest getAuthorRequest() {
        return authorRequest;
    }

    public BookRequest getBookRequest() {
        return bookRequest;
    }
}
