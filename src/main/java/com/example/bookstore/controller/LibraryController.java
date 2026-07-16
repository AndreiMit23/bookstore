package com.example.bookstore.controller;

import com.example.bookstore.dto.BookWithAuthorRequest;
import com.example.bookstore.dto.BookWithAuthorResponse;
import com.example.bookstore.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Cross-resource operations that create/modify more than one entity at once. */
@RestController
@RequestMapping("/api/library")
@Tag(name = "Library", description = "Cross-resource operations spanning more than one entity.")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/book-with-author")
    @Operation(summary = "Create a book together with a new author",
            description = "Creates a new author and a new book, links them (M:N), all in one transaction.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Author and book created and linked"),
        @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<BookWithAuthorResponse> createBookWithAuthor(
            @Valid @RequestBody BookWithAuthorRequest request) {
        BookWithAuthorResponse created = libraryService.createBookWithAuthor(request);
        return ResponseEntity.created(URI.create("/api/books/" + created.book().id())).body(created);
    }
}
