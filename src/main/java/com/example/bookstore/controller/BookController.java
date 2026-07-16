package com.example.bookstore.controller;

import com.example.bookstore.dto.BookProfileDto;
import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "CRUD for books, author linking (M:N) and the 1:1 profile.")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "List all books")
    public List<BookResponse> list() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book found"),
        @ApiResponse(responseCode = "404", description = "No book with that id")
    })
    public BookResponse get(@Parameter(description = "Book id") @PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a book",
            description = "Relationships are referenced by id: publisherId (M:1) and authorIds (M:N). An optional profile cascades into the 1:1 BookProfile.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Book created"),
        @ApiResponse(responseCode = "400", description = "Validation failed (title blank)"),
        @ApiResponse(responseCode = "404", description = "Referenced publisher or author not found")
    })
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.created(URI.create("/api/books/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book updated"),
        @ApiResponse(responseCode = "400", description = "Validation failed"),
        @ApiResponse(responseCode = "404", description = "Book, publisher or author not found")
    })
    public BookResponse update(@Parameter(description = "Book id") @PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Book deleted"),
        @ApiResponse(responseCode = "404", description = "No book with that id")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Book id") @PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/authors/{authorId}")
    @Operation(summary = "Link an author to a book", description = "Many-to-many: adds an existing author to this book.")
    @ApiResponse(responseCode = "404", description = "Book or author not found")
    public BookResponse addAuthor(@Parameter(description = "Book id") @PathVariable Long id,
            @Parameter(description = "Author id to link") @PathVariable Long authorId) {
        return bookService.addAuthor(id, authorId);
    }

    @DeleteMapping("/{id}/authors/{authorId}")
    @Operation(summary = "Unlink an author from a book", description = "Many-to-many: removes an author from this book.")
    @ApiResponse(responseCode = "404", description = "Book or author not found")
    public BookResponse removeAuthor(@Parameter(description = "Book id") @PathVariable Long id,
            @Parameter(description = "Author id to unlink") @PathVariable Long authorId) {
        return bookService.removeAuthor(id, authorId);
    }

    @PutMapping("/{id}/profile")
    @Operation(summary = "Set or clear a book's profile",
            description = "One-to-one management. Provide a body to set/update the profile; send no body to clear it.")
    @ApiResponse(responseCode = "404", description = "No book with that id")
    public BookResponse setProfile(@Parameter(description = "Book id") @PathVariable Long id,
            @RequestBody(required = false) BookProfileDto profile) {
        return bookService.setProfile(id, profile);
    }
}
