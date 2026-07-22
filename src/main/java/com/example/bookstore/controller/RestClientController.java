package com.example.bookstore.controller;

import com.example.bookstore.mapper.IsbnData;
import com.example.bookstore.service.OpenLibraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestClientController {

    private final OpenLibraryService openLibraryService;

    public RestClientController(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    @GetMapping("openLibrary/{isbn}")
    public IsbnData searchByIsbn(@PathVariable String isbn){
        return openLibraryService.searchByIsbn(isbn);
    }
}
