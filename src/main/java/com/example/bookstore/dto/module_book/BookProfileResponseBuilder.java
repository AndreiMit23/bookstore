package com.example.bookstore.dto.module_book;

public class BookProfileResponseBuilder {
    private Long id;
    private String genre;
    private Integer pages;
    private String language;

    public static BookProfileResponseBuilder builder(){
        return new BookProfileResponseBuilder();
    }

    public BookProfileResponseBuilder id(Long id){
        this.id = id;
        return this;
    }

    public BookProfileResponseBuilder genre(String genre){
        this.genre = genre;
        return this;
    }

    public BookProfileResponseBuilder pages(Integer pages){
        this.pages = pages;
        return this;
    }

    public BookProfileResponseBuilder language(String language){
        this.language = language;
        return this;
    }

    public BookProfileResponse build(){
        return new BookProfileResponse(id,genre,pages,language);
    }
}
