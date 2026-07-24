package com.example.bookstore.dto.module_book;

public class BookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private Integer publicationYear;

    private BookProfileResponse bookProfile;

    public BookResponse(Long id, String title, String description, Integer publicationYear){
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookProfileResponse getBookProfile() {
        return bookProfile;
    }

    public void setBookProfile(BookProfileResponse bookProfile) {
        this.bookProfile = bookProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
