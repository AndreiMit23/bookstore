package com.example.bookstore.module;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
