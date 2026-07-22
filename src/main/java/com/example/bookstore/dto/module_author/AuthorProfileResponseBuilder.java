package com.example.bookstore.dto.module_author;

public class AuthorProfileResponseBuilder {
    private String biography;
    private String website;

    public static AuthorProfileResponseBuilder builder(){
        return new AuthorProfileResponseBuilder();
    }

    public AuthorProfileResponseBuilder biography(String biography){
        this.biography = biography;
        return this;
    }

    public AuthorProfileResponseBuilder website(String website){
        this.website = website;
        return this;
    }

    public AuthorProfileResponse build(){
        return new AuthorProfileResponse(biography,website);
    }
}
