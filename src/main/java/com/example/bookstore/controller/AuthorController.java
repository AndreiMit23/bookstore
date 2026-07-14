package com.example.bookstore.controller;

import com.example.bookstore.dto.AuthorProfileDto;
import com.example.bookstore.dto.AuthorRequest;
import com.example.bookstore.dto.AuthorResponse;
import com.example.bookstore.dto.BookSummary;
import com.example.bookstore.entity.Author;
import com.example.bookstore.service.AuthorService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorResponse> list() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public AuthorResponse get(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse created = authorService.create(request);
        return ResponseEntity.created(URI.create("/api/authors/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public AuthorResponse update(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) {
        return authorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Many-to-many view: the books this author has (co-)written. */
    @GetMapping("/{id}/books")
    public List<BookSummary> books(@PathVariable Long id) {
        return authorService.findBooks(id);
    }

    /** One-to-one management: set/update the author's profile (null body clears it). */
    @PutMapping("/{id}/profile")
    public AuthorResponse setProfile(@PathVariable Long id, @RequestBody(required = false) AuthorProfileDto profile) {
        return authorService.setProfile(id, profile);
    }

    /**
     * DEMO ONLY: binds the {@link Author} entity directly. {@code @Valid} is a no-op here
     * because the entity carries no bean-validation constraints; response still mapped to
     * a DTO to avoid serializing the entity graph.
     */
    @PostMapping("/entity")
    public ResponseEntity<AuthorResponse> createFromEntity(@Valid @RequestBody Author author) {
        AuthorResponse created = authorService.createFromEntity(author);
        return ResponseEntity.created(URI.create("/api/authors/" + created.id())).body(created);
    }
}
