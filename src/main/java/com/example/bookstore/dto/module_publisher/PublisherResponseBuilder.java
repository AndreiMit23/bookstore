package com.example.bookstore.dto.module_publisher;

import com.example.bookstore.dto.module_book.BookResponse;

import java.util.List;

public class PublisherResponseBuilder {
    private Long id;
    private String name;
    private String city;
    private List<BookResponse> bookResponseList;

    public static PublisherResponseBuilder builder(){
        return new PublisherResponseBuilder();
    }

    public PublisherResponseBuilder id(Long id){
        this.id = id;
        return this;
    }

    public PublisherResponseBuilder name(String name){
        this.name = name;
        return this;
    }

    public PublisherResponseBuilder city(String city){
        this.city = city;
        return this;
    }

    public PublisherResponseBuilder bookResponseList(List<BookResponse> bookResponseList){
        this.bookResponseList = bookResponseList;
        return this;
    }

    public PublisherResponse build(){
        PublisherResponse publisherResponse = new PublisherResponse(id,name,city);

        publisherResponse.setBookResponseList(bookResponseList);
        return publisherResponse;
    }
}
