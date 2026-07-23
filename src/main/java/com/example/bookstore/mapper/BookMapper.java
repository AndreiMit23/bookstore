package com.example.bookstore.mapper;

import com.example.bookstore.dto.module_book.*;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {
    public Book toEntity(BookRequest bookRequest){
        Book book = new Book(bookRequest.getTitle(),bookRequest.getDescription(),bookRequest.getPublicationYear());

        if(bookRequest.getBookProfile() != null){
            BookProfile bookProfile = toProfileEntity(bookRequest.getBookProfile());

            book.setBookProfile(bookProfile);
            bookProfile.setBook(book);
        }

        return book;
    }

    public BookProfile toProfileEntity(BookProfileRequest bookProfileRequest){
        return new BookProfile(bookProfileRequest.getGenre(),bookProfileRequest.getPages(),bookProfileRequest.getLanguage());
    }

    public BookResponse toResponse(Book book){
        BookResponse bookResponse = BookResponseBuilder.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .publicationYear(book.getPublicationYear())
                .build();
        BookProfileResponse bookProfileResponse = null;

        if(book.getBookProfile() != null){
            bookProfileResponse = toProfileResponse(book.getBookProfile());

            bookResponse.setBookProfile(bookProfileResponse);
        }

        return bookResponse;
    }

    public BookProfileResponse toProfileResponse(BookProfile bookProfile){
        return BookProfileResponseBuilder.builder().id(bookProfile.getId()).genre(bookProfile.getGenre()).pages(bookProfile.getPages()).language(bookProfile.getLanguage()).build();
    }

    public List<BookResponse> toResponseList(List<Book> bookList){
        List<BookResponse> bookResponses = new ArrayList<>();

        for(Book book : bookList){
            bookResponses.add(toResponse(book));
        }

        return bookResponses;
    }
}
