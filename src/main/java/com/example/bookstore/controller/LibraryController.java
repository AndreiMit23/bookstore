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

    @PostMapping("/link")
    public void linkAuthorAndBook(
            @RequestBody LinkAuthorBookRequest request) {

        libraryService.linkAuthorAndBook(request);
    }

    @PostMapping
    public AuthorAndBookResponse saveLibrary(@RequestBody AuthorAndBookRequest authorAndBookRequest){
        return libraryService.saveLibrary(authorAndBookRequest);
    }

    @GetMapping
    public AuthorAndBookResponse getLibrary(){
        return libraryService.getLibrary();
    }

    @DeleteMapping("/{authorId}/{bookId}")
    public void deleteLibrary(@PathVariable Long authorId, @PathVariable Long bookId){
        libraryService.deleteLibrary(authorId,bookId);
    }
}
