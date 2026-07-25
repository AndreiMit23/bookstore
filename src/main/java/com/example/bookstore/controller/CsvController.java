package com.example.bookstore.controller;

import com.example.bookstore.service.CsvService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CsvController {
    private final CsvService csvService;

    public CsvController(CsvService csvService){
        this.csvService = csvService;
    }

    @PostMapping("/csv/{fileName}")
    public void importCsv(@PathVariable String fileName){
        csvService.importCsv(fileName);
    }

}
