package com.example.bookstore.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class AuthorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    private String biography;
    @Column
    private String website;
    @OneToOne
    @JoinColumn(name = "author_id")
//    @MapsId
    Author author;
    public AuthorProfile(String biography, String website){
        this.biography = biography;
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthorProfile that = (AuthorProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(biography, that.biography) && Objects.equals(website, that.website) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, biography, website, author);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public AuthorProfile() {

    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long ID) {
        this.id = ID;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
