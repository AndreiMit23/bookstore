package com.example.bookstore.dto.module_author;

public class AuthorRequest {
    private String firstName;
    private String lastName;

    private AuthorProfileRequest authorProfile;

    public AuthorRequest(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorProfileRequest getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfileRequest authorProfile) {
        this.authorProfile = authorProfile;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName() {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName() {
        this.lastName = lastName;
    }
}
