package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.dto.module_author.AuthorResponse;
import com.example.bookstore.dto.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.dto.module_author_book.BookWithAuthorResponse;
import jakarta.transaction.Transactional;
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

    @Transactional
    public BookWithAuthorResponse saveBookWithAuthor(AuthorAndBookRequest authorAndBookRequest){
        Author author = authorService.saveAuthor(authorAndBookRequest.getAuthorRequest());
        Book book = bookService.saveBook(authorAndBookRequest.getBookRequest());

        book.getAuthors().add(author);
        author.getBooks().add(book);

        return new BookWithAuthorResponse(authorService.toResponse(author), bookService.toResponse(book));
    }

    public List<AuthorResponse> getBookWithAuthor(){
        return authorService.getAuthors();
    }
}
