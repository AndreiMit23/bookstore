package com.example.bookstore.module_author;

public class AuthorProfileResponse {
    private String biography;
    private String website;

    public AuthorProfileResponse(String biography, String website){
        this.biography = biography;
        this.website = website;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
