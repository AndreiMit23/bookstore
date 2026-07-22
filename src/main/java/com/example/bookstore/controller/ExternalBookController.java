package com.example.bookstore.controller;

import com.example.bookstore.dto.ExternalBookDetail;
import com.example.bookstore.dto.ExternalBookSearchResponse;
import com.example.bookstore.service.OpenLibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** Read-only lookups against the Open Library public API (no data is persisted locally). */
@RestController
@RequestMapping("/api/external/books")
@Validated
@Tag(name = "External Books", description = "Retrieve book data from the Open Library public API.")
public class ExternalBookController {

    private final OpenLibraryService openLibraryService;

    public ExternalBookController(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    @GetMapping
    @Operation(summary = "Search Open Library by query",
            description = "Full-text search against Open Library; returns the total match count and the mapped results.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Search executed"),
        @ApiResponse(responseCode = "400", description = "Query parameter 'q' is missing or blank"),
        @ApiResponse(responseCode = "502", description = "Open Library is unreachable or returned an error")
    })
    public ExternalBookSearchResponse search(
            @Parameter(description = "Free-text search query, e.g. a title or author")
            @RequestParam("q") @NotBlank String q) {
        return openLibraryService.search(q);
    }

    @GetMapping("/{isbn}")
    @Operation(summary = "Look up a book by ISBN",
            description = "Fetches a single book's details from Open Library by ISBN-10 or ISBN-13.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book found"),
        @ApiResponse(responseCode = "404", description = "No book with that ISBN on Open Library"),
        @ApiResponse(responseCode = "502", description = "Open Library is unreachable or returned an error")
    })
    public ExternalBookDetail lookupByIsbn(
            @Parameter(description = "ISBN-10 or ISBN-13") @PathVariable String isbn) {
        return openLibraryService.lookupByIsbn(isbn);
    }
}
