package com.example.bookstore.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
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

    @ManyToOne
    @JoinColumn(name = "publisher_ID")
    Publisher publisher;

    //TODO: un library service care are ca parametrii / importa AuthorService si BookService
    //un nou library controller care foloseste un library service si asta se foloseste de AuthprServoce so Bookservice
    //care salveaza in DB un Author si Book in acelasi endpoint
    // pot sa mi fac un BookWithAuthorRequest - parametrul de la controller ... are 2 parametri : authorrequest si bookrequest

    public Book(String title, String description, Integer publicationYear){
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
    }

    public Book(Long Id) {
        this.Id = Id;
    }

    public Book() {

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

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Id != null && Objects.equals(Id, book.Id);
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }
}
