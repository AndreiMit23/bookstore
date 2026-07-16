package com.example.bookstore.controller;

import com.example.bookstore.dto.AuthorProfileDto;
import com.example.bookstore.dto.AuthorRequest;
import com.example.bookstore.dto.AuthorResponse;
import com.example.bookstore.dto.BookSummary;
import com.example.bookstore.entity.Author;
import com.example.bookstore.service.AuthorService;
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
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "CRUD for authors, plus their books (M:N) and 1:1 profile.")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "List all authors")
    public List<AuthorResponse> list() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Author found"),
        @ApiResponse(responseCode = "404", description = "No author with that id")
    })
    public AuthorResponse get(@Parameter(description = "Author id") @PathVariable Long id) {
        return authorService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create an author", description = "An optional profile in the body cascades into the 1:1 AuthorProfile.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Author created"),
        @ApiResponse(responseCode = "400", description = "Validation failed (firstName/lastName blank)")
    })
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse created = authorService.create(request);
        return ResponseEntity.created(URI.create("/api/authors/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Author updated"),
        @ApiResponse(responseCode = "400", description = "Validation failed"),
        @ApiResponse(responseCode = "404", description = "No author with that id")
    })
    public AuthorResponse update(@Parameter(description = "Author id") @PathVariable Long id, @Valid @RequestBody AuthorRequest request) {
        return authorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Author deleted"),
        @ApiResponse(responseCode = "404", description = "No author with that id")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Author id") @PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "List an author's books", description = "Many-to-many view: the books this author has (co-)written.")
    @ApiResponse(responseCode = "404", description = "No author with that id")
    public List<BookSummary> books(@Parameter(description = "Author id") @PathVariable Long id) {
        return authorService.findBooks(id);
    }

    @PutMapping("/{id}/profile")
    @Operation(summary = "Set or clear an author's profile",
            description = "One-to-one management. Provide a body to set/update the profile; send no body to clear it.")
    @ApiResponse(responseCode = "404", description = "No author with that id")
    public AuthorResponse setProfile(@Parameter(description = "Author id") @PathVariable Long id,
            @RequestBody(required = false) AuthorProfileDto profile) {
        return authorService.setProfile(id, profile);
    }

    /**
     * DEMO ONLY: binds the {@link Author} entity directly. {@code @Valid} is a no-op here
     * because the entity carries no bean-validation constraints; response still mapped to
     * a DTO to avoid serializing the entity graph.
     */
    @PostMapping("/entity")
    @Operation(summary = "Create an author from the entity (demo)",
            description = "DEMO ONLY: binds the JPA entity directly rather than a request DTO.")
    public ResponseEntity<AuthorResponse> createFromEntity(@Valid @RequestBody Author author) {
        AuthorResponse created = authorService.createFromEntity(author);
        return ResponseEntity.created(URI.create("/api/authors/" + created.id())).body(created);
    }
}
