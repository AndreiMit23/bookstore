package com.example.bookstore.controller;

import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.AuthorAndBookResponse;
import com.example.bookstore.module_author_book.LinkAuthorBookRequest;
import com.example.bookstore.service.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author-book")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }


}
