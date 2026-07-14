package com.example.bookstore.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;
    @Column
    String title;
    @Column
    String description;
    @Column
    Integer publicationYear;
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_ID"), inverseJoinColumns = @JoinColumn(name = "author_ID"))
    Set<Author> authors = new LinkedHashSet<>();

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    BookProfile bookProfile;

    public Book(String title, String description, Integer publicationYear){
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public Book(Long ID) {
        this.ID = ID;
    }

    public Book() {

    }

    public BookProfile getBookProfile() {
        return bookProfile;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return ID != null && Objects.equals(ID, book.ID);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

    public void setBookProfile(BookProfile bookProfile) {
        this.bookProfile = bookProfile;
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long id) {
        this.ID = id;
    }
}
