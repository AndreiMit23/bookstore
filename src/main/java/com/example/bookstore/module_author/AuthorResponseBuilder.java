package com.example.bookstore.module_author;

import com.example.bookstore.module_book.BookResponse;

import java.util.List;

public class AuthorResponseBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private AuthorProfileResponse authorProfile;
    private List<BookResponse> books;

    public static AuthorResponseBuilder builder(){
        return new AuthorResponseBuilder();
    }

    public AuthorResponseBuilder id(Long id){
        this.id = id;
        return this;
    }

    public AuthorResponseBuilder firstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public AuthorResponseBuilder lastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public AuthorResponseBuilder authorProfile(AuthorProfileResponse authorProfile){
        this.authorProfile = authorProfile;
        return this;
    }

    public AuthorResponseBuilder books(List<BookResponse> books){
        this.books = books;
        return this;
    }

    public AuthorResponse build(){
        AuthorResponse authorResponse = new AuthorResponse(id,firstName,lastName,authorProfile);
        authorResponse.setBooks(books);

        return authorResponse;
    }

}
