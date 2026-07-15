package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.AuthorAndBookResponse;
import com.example.bookstore.module_book.BookResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final AuthorService authorService;
    private final BookService bookService;

    public LibraryService(AuthorService authorService, BookService bookService){
        this.authorService = authorService;
        this.bookService = bookService;
    }

    public AuthorAndBookResponse saveLibrary(AuthorAndBookRequest authorAndBookRequest){
        List<AuthorResponse> authorResponseList = authorService.saveAuthors(authorAndBookRequest.getAuthorRequest());
        List<BookResponse> bookResponseList = bookService.saveBooks(authorAndBookRequest.getBookRequest());

        return new AuthorAndBookResponse(authorResponseList,bookResponseList);
    }

    public AuthorAndBookResponse getLibrary(){
        List<AuthorResponse> authorResponses = authorService.getAuthors();
        List<BookResponse> bookResponses = bookService.getBooks();

        return new AuthorAndBookResponse(authorResponses,bookResponses);
    }

    public void deleteLibrary(Long authorId, Long bookId){
        authorService.deleteAuthor(authorId);
        bookService.deleteBook(bookId);
    }
}
