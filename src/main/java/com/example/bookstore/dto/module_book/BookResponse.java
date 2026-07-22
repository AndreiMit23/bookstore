package com.example.bookstore.dto.module_book;

public class BookResponse {
    private Long ID;
    private String title;
    private String description;
    private Integer publicationYear;

    private BookProfileResponse bookProfile;

    public BookResponse(Long ID, String title, String description, Integer publicationYear){
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public BookProfileResponse getBookProfile() {
        return bookProfile;
    }

    public void setBookProfile(BookProfileResponse bookProfile) {
        this.bookProfile = bookProfile;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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
