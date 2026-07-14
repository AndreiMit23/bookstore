package com.example.bookstore.mapper;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookProfile;
import com.example.bookstore.module_book.BookProfileRequest;
import com.example.bookstore.module_book.BookProfileResponse;
import com.example.bookstore.module_book.BookRequest;
import com.example.bookstore.module_book.BookResponse;
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
        BookResponse bookResponse = new BookResponse(book.getID(),book.getTitle(),book.getDescription(),book.getPublicationYear());

        BookProfileResponse bookProfileResponse = null;

        if(book.getBookProfile() != null){
            bookProfileResponse = toProfileResponse(book.getBookProfile());

            bookResponse.setBookProfile(bookProfileResponse);
        }

        return bookResponse;
    }

    public BookProfileResponse toProfileResponse(BookProfile bookProfile){
        return new BookProfileResponse(bookProfile.getID(),bookProfile.getGenre(),bookProfile.getPages(),bookProfile.getLanguage());
    }

    public List<BookResponse> toResponseList(List<Book> bookList){
        List<BookResponse> bookResponses = new ArrayList<>();

        for(Book book : bookList){
            bookResponses.add(toResponse(book));
        }

        return bookResponses;
    }
}
