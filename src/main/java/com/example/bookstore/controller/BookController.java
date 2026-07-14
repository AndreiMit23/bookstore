package com.example.bookstore.controller;

import com.example.bookstore.dto.BookProfileDto;
import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> list() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.created(URI.create("/api/books/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Link an existing author to this book (many-to-many). */
    @PostMapping("/{id}/authors/{authorId}")
    public BookResponse addAuthor(@PathVariable Long id, @PathVariable Long authorId) {
        return bookService.addAuthor(id, authorId);
    }

    /** Unlink an author from this book (many-to-many). */
    @DeleteMapping("/{id}/authors/{authorId}")
    public BookResponse removeAuthor(@PathVariable Long id, @PathVariable Long authorId) {
        return bookService.removeAuthor(id, authorId);
    }

    /** One-to-one management: set/update the book's profile (null body clears it). */
    @PutMapping("/{id}/profile")
    public BookResponse setProfile(@PathVariable Long id, @RequestBody(required = false) BookProfileDto profile) {
        return bookService.setProfile(id, profile);
    }
}
