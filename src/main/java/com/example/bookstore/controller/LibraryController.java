package com.example.bookstore.controller;

import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.BookWithAuthorResponse;
import com.example.bookstore.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author-book")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @PostMapping
    public BookWithAuthorResponse save(@RequestBody AuthorAndBookRequest authorAndBookRequest){
        return libraryService.saveBookWithAuthor(authorAndBookRequest);
    }

    @GetMapping
    public List<AuthorResponse> get(){
        return libraryService.getBookWithAuthor();
    }

}
