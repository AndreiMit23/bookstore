package com.example.bookstore.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class BookProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @Column
    String genre;
    @Column
    Integer pages;
    @Column
    String language;

    @OneToOne
    @JoinColumn(name = "book_id")
    Book book;

    public BookProfile(String genre, Integer pages, String language){
        this.genre = genre;
        this.pages = pages;
        this.language = language;
    }

    public BookProfile() {

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass())
            return false;
        BookProfile bookProfile = (BookProfile) o;
        return Id != null && Objects.equals(Id, bookProfile.Id);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long ID) {
        this.Id = ID;
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
