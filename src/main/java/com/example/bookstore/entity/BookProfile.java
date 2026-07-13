package com.example.bookstore.entity;

import jakarta.persistence.*;

@Entity
public class BookProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;
    @Column
    String genre;
    @Column
    Integer pages;
    @Column
    String language;

    public BookProfile(String genre, Integer pages, String language){
        this.genre = genre;
        this.pages = pages;
        this.language = language;
    }

    public BookProfile() {

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
