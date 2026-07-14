package com.example.bookstore.module_book;

public class BookProfileResponse {
    private Long ID;
    private String genre;
    private Integer pages;
    private String language;

    public BookProfileResponse(Long ID, String genre, Integer pages, String language){
        this.ID = ID;
        this.genre = genre;
        this.pages = pages;
        this.language = language;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
