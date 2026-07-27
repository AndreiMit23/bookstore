package com.example.bookstore.controller;

import com.example.bookstore.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CsvController {
    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/csv/{fileName}")
    public void importCsv(@PathVariable String fileName) {
        csvService.importCsv(fileName);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> importFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) { return ResponseEntity.badRequest().body("File is empty."); }
        String contentType = file.getContentType();
        if (!"text/csv".equals(contentType) && !file.getOriginalFilename().endsWith(".csv")) { return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE) .body("Only CSV files are allowed."); }
        csvService.importCsvMultiPart(file);
        return ResponseEntity.ok("Successfully imported ");
    }
}