package com.example.bookstore.controller;

import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.module_author_book.BookWithAuthorResponse;
import com.example.bookstore.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Save book and author", description = "Returns a book and an author via the request body")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book and author saved"),
            @ApiResponse(responseCode = "404", description = "Book and author not saved")
    })
    public BookWithAuthorResponse save(@RequestBody AuthorAndBookRequest authorAndBookRequest){
        return libraryService.saveBookWithAuthor(authorAndBookRequest);
    }

    @GetMapping
    @Operation(summary = "Gets books and authors", description = "Returns a list of books and authors that are associated through id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book and author found"),
            @ApiResponse(responseCode = "404", description = "Book and author not found")
    })
    public List<AuthorResponse> get(){
        return libraryService.getBookWithAuthor();
    }

}
