package com.example.bookstore.module_author;

public class AuthorResponse {
    private Long ID;
    private String firstName;
    private String lastName;
    private AuthorProfileResponse authorProfile;

    public AuthorResponse(Long ID, String firstName, String lastName, AuthorProfileResponse authorProfile){
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorProfile = authorProfile;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public AuthorProfileResponse getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfileResponse authorProfile) {
        this.authorProfile = authorProfile;
    }
}
