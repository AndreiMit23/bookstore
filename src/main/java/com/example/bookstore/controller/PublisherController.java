package com.example.bookstore.controller;

import com.example.bookstore.dto.BookSummary;
import com.example.bookstore.dto.PublisherRequest;
import com.example.bookstore.dto.PublisherResponse;
import com.example.bookstore.service.PublisherService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherResponse> list() {
        return publisherService.findAll();
    }

    @GetMapping("/{id}")
    public PublisherResponse get(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    @PostMapping
    public ResponseEntity<PublisherResponse> create(@Valid @RequestBody PublisherRequest request) {
        PublisherResponse created = publisherService.create(request);
        return ResponseEntity.created(URI.create("/api/publishers/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public PublisherResponse update(@PathVariable Long id, @Valid @RequestBody PublisherRequest request) {
        return publisherService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** One-to-many view: the books released by this publisher. */
    @GetMapping("/{id}/books")
    public List<BookSummary> books(@PathVariable Long id) {
        return publisherService.findById(id).books();
    }
}
