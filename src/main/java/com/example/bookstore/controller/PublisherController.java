package com.example.bookstore.controller;

import com.example.bookstore.dto.BookSummary;
import com.example.bookstore.dto.PublisherRequest;
import com.example.bookstore.dto.PublisherResponse;
import com.example.bookstore.service.PublisherService;
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
@RequestMapping("/api/publishers")
@Tag(name = "Publishers", description = "CRUD for publishers and the books they released (1:N).")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    @Operation(summary = "List all publishers")
    public List<PublisherResponse> list() {
        return publisherService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a publisher by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Publisher found"),
        @ApiResponse(responseCode = "404", description = "No publisher with that id")
    })
    public PublisherResponse get(@Parameter(description = "Publisher id") @PathVariable Long id) {
        return publisherService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a publisher")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Publisher created"),
        @ApiResponse(responseCode = "400", description = "Validation failed (name blank)")
    })
    public ResponseEntity<PublisherResponse> create(@Valid @RequestBody PublisherRequest request) {
        PublisherResponse created = publisherService.create(request);
        return ResponseEntity.created(URI.create("/api/publishers/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a publisher")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Publisher updated"),
        @ApiResponse(responseCode = "400", description = "Validation failed"),
        @ApiResponse(responseCode = "404", description = "No publisher with that id")
    })
    public PublisherResponse update(@Parameter(description = "Publisher id") @PathVariable Long id, @Valid @RequestBody PublisherRequest request) {
        return publisherService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a publisher")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Publisher deleted"),
        @ApiResponse(responseCode = "404", description = "No publisher with that id")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Publisher id") @PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "List a publisher's books", description = "One-to-many view: the books released by this publisher.")
    @ApiResponse(responseCode = "404", description = "No publisher with that id")
    public List<BookSummary> books(@Parameter(description = "Publisher id") @PathVariable Long id) {
        return publisherService.findById(id).books();
    }
}
