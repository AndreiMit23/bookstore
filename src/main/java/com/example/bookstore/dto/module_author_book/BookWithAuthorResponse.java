package com.example.bookstore.dto.module_author_book;

import com.example.bookstore.dto.module_author.AuthorResponse;
import com.example.bookstore.dto.module_book.BookResponse;

public class BookWithAuthorResponse {
    private AuthorResponse authorResponse;
    private BookResponse bookResponse;

    public BookWithAuthorResponse(AuthorResponse authorResponse, BookResponse bookResponse) {
        this.authorResponse = authorResponse;
        this.bookResponse = bookResponse;
    }

    public AuthorResponse getAuthorResponse() {
        return authorResponse;
    }

    public void setAuthorResponse(AuthorResponse authorResponse) {
        this.authorResponse = authorResponse;
    }

    public BookResponse getBookResponse() {
        return bookResponse;
    }

    public void setBookResponse(BookResponse bookResponse) {
        this.bookResponse = bookResponse;
    }
}
