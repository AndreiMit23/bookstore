package com.example.bookstore.module_book;

public class BookRequest {
    private String title;
    private String description;
    private Integer publicationYear;

    public BookRequest(String title, String description, Integer publicationYear){
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
}
