package com.example.bookstore.dto.module_author;

import com.example.bookstore.dto.module_book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private AuthorProfileResponse authorProfile;
    private List<BookResponse> books;
}
