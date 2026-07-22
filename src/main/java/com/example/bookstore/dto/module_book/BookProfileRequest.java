package com.example.bookstore.dto.module_book;

public class BookProfileRequest {
    private String genre;
    private Integer pages;
    private String language;

    public BookProfileRequest(String genre, Integer pages, String language){
        this.genre = genre;
        this.pages = pages;
        this.language = language;
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
