package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.module_book.BookRequest;
import com.example.bookstore.module_book.BookResponse;
import com.example.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "This endpoint returns a list of books", description = "It has the role to return all of the books stored in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "BOOK WAS FOUND"),
            @ApiResponse(responseCode = "404", description = "BOOK WA NOT FOUND")
    })
    public List<BookResponse> getBooks(){
        return bookService.getBooks();
    }

    @PostMapping
    public List<BookResponse> saveBooks(@RequestBody BookRequest bookRequest){
        return bookService.saveBooks(bookRequest);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest){
        bookService.updateBook(id,bookRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

}
