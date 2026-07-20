package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.mapper.AuthorMapper;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.AuthorAndBookResponse;
import com.example.bookstore.module_author_book.BookWithAuthorResponse;
import com.example.bookstore.module_author_book.LinkAuthorBookRequest;
import com.example.bookstore.module_book.BookResponse;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public LibraryService(AuthorService authorService, BookService bookService, BookMapper bookMapper,AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookWithAuthorResponse saveBookWithAuthor(AuthorAndBookRequest authorAndBookRequest){
        Author author = authorService.saveAuthor(authorAndBookRequest.getAuthorRequest());
        Book book = bookService.saveBook(authorAndBookRequest.getBookRequest());

        book.getAuthors().add(author);
        author.getBooks().add(book);

        return new BookWithAuthorResponse(authorMapper.toResponse(author), bookMapper.toResponse(book));

    }
}
