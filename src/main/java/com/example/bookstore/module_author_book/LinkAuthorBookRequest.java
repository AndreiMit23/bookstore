package com.example.bookstore.module_author_book;

public class LinkAuthorBookRequest {
    private Long authorId;
    private Long bookId;

    public LinkAuthorBookRequest(){

    }

    public LinkAuthorBookRequest(Long authorId, Long bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
