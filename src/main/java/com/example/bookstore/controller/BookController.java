package com.example.bookstore.controller;

import com.example.bookstore.dto.module_book.BookRequest;
import com.example.bookstore.dto.module_book.BookResponse;
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
            @ApiResponse(responseCode = "404", description = "BOOK WAS NOT FOUND")
    })
    public List<BookResponse> getBooks(){
        return bookService.getBooks();
    }

    @PostMapping
    @Operation(summary = "Save a list of books", description = "Returns a list of books from the request body, one or more books can be created")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book saved"),
            @ApiResponse(responseCode = "400", description = "Bad request for saving")
    })
    public List<BookResponse> saveBooks(@RequestBody BookRequest bookRequest){
        return bookService.saveBooks(bookRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book",description = "Updates a book via id endpoint and a request body")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "400", description = "Bad request, the book was not created")
    })
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest){
        bookService.updateBook(id,bookRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Deletes a book via id endpoint")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book deleted"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

}
