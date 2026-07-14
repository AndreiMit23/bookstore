package com.example.bookstore.controller;

import com.example.bookstore.dto.BookWithAuthorRequest;
import com.example.bookstore.dto.BookWithAuthorResponse;
import com.example.bookstore.service.LibraryService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Cross-resource operations that create/modify more than one entity at once. */
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /** Creates a new author and a new book together, linked (many-to-many), in one transaction. */
    @PostMapping("/book-with-author")
    public ResponseEntity<BookWithAuthorResponse> createBookWithAuthor(
            @Valid @RequestBody BookWithAuthorRequest request) {
        BookWithAuthorResponse created = libraryService.createBookWithAuthor(request);
        return ResponseEntity.created(URI.create("/api/books/" + created.book().id())).body(created);
    }
}
