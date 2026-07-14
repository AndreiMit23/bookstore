package com.example.bookstore.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String title;
    @Column
    String description;
    @Column
    Integer publicationYear;
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_ID"), inverseJoinColumns = @JoinColumn(name = "author_ID"))
    Set<Author> authors = new LinkedHashSet<>();

    // NEW: many-to-one — many books belong to one publisher (this side owns the FK).
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    Publisher publisher;

    // NEW: one-to-one — a book has extended detail. FK-based (book_profile_id column),
    // matching how AuthorProfile is wired here rather than the demo's @MapsId.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_profile_id")
    BookProfile bookProfile;

    public Book(String title, String description, Integer publicationYear){
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public Book(Long id) {
        this.id = id;
    }

    public Book() {

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

    // Keep both sides of the many-to-many consistent in memory.
    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getBooks().remove(this);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public BookProfile getBookProfile() {
        return bookProfile;
    }

    public void setBookProfile(BookProfile bookProfile) {
        this.bookProfile = bookProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
