package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.AuthorAndBookResponse;
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
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public LibraryService(AuthorService authorService, BookService bookService, AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorService = authorService;
        this.bookService = bookService;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void linkAuthorAndBook(LinkAuthorBookRequest request){
        Author author = authorRepository.findById(request.getAuthorId()).orElseThrow();
        Book book = bookRepository.findById(request.getBookId()).orElseThrow();

        author.getBooks().add(book);
        book.getAuthors().add(author);
        bookRepository.save(book);
    }

    public AuthorAndBookResponse saveLibrary(AuthorAndBookRequest authorAndBookRequest){
        List<AuthorResponse> authorResponseList = authorService.saveAuthors(authorAndBookRequest.getAuthorRequest());

        return new AuthorAndBookResponse(authorResponseList);
    }

    public AuthorAndBookResponse getLibrary(){
        List<AuthorResponse> authorResponses = authorService.getAuthors();
        return new AuthorAndBookResponse(authorResponses);
    }

    public void deleteLibrary(Long authorId, Long bookId){
        authorService.deleteAuthor(authorId);
        bookService.deleteBook(bookId);
    }
}
