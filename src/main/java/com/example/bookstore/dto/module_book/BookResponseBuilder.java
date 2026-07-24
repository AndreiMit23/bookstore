package com.example.bookstore.dto.module_book;

public class BookResponseBuilder {
    private String isbn;
    private Long id;
    private String title;
    private String description;
    private Integer publicationYear;

    private BookProfileResponse bookProfile;

    public static BookResponseBuilder builder(){
        return new BookResponseBuilder();
    }

    public BookResponseBuilder isbn(String isbn){
        this.isbn = isbn;
        return this;
    }

    public BookResponseBuilder id(Long id){
        this.id = id;
        return this;
    }

    public BookResponseBuilder title(String title){
        this.title = title;
        return this;
    }

    public BookResponseBuilder description(String description){
        this.description = description;
        return this;
    }

    public BookResponseBuilder publicationYear(Integer publicationYear){
        this.publicationYear = publicationYear;
        return this;
    }

    public BookResponseBuilder bookProfile(BookProfileResponse bookProfileResponse){
        this.bookProfile = bookProfileResponse;
        return this;
    }

    public BookResponse build(){
        BookResponse bookResponse = new BookResponse(id,title,description,publicationYear);

        bookResponse.setBookProfile(bookProfile);
        bookResponse.setIsbn(isbn);
        return bookResponse;
    }
}
