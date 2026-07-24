package com.example.bookstore.dto.module_book;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class ExternalBookResponse {
    private Long id;
    private String title;
    private String description;
    private Integer publicationYear;
    private String isbn;
    private BookProfileResponse bookProfileResponse;
}

