package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.AuthorAndBookResponse;
import com.example.bookstore.module_author_book.BookWithAuthorResponse;
import com.example.bookstore.module_author_book.LinkAuthorBookRequest;
import com.example.bookstore.module_book.BookResponse;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final AuthorService authorService;
    private final BookService bookService;

    public LibraryService(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    public BookWithAuthorResponse saveBookWithAuthor(AuthorAndBookRequest authorAndBookRequest){
        authorService.saveAuthors(authorAndBookRequest.getAuthorRequest());
        bookService.saveBooks(authorAndBookRequest.getBookRequest())
    }
}
