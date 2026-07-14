package com.example.bookstore.module_publisher;

import com.example.bookstore.module_book.BookResponse;

import java.util.List;

public class PublisherResponse {
    private Long ID;
    private String name;
    private String city;
    private List<BookResponse> bookResponseList;

    public PublisherResponse(Long ID, String name, String city){
        this.ID = ID;
        this.name = name;
        this.city = city;
    }

    public List<BookResponse> getBookResponseList() {
        return bookResponseList;
    }

    public void setBookResponseList(List<BookResponse> bookResponseList) {
        this.bookResponseList = bookResponseList;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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
