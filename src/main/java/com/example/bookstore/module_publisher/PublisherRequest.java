package com.example.bookstore.module_publisher;

public class PublisherRequest {
    private String name;
    private String city;
    private Long bookId;

    public PublisherRequest(String name, String city, Long bookId){
        this.name = name;
        this.city = city;
        this.bookId = bookId;
    }

    public PublisherRequest(String name, String city){
        this.name = name;
        this.city = city;
    }

    public PublisherRequest(){

    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
