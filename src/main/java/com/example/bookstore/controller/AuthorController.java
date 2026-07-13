package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.module_author.AuthorRequest;
import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }
    @GetMapping
    public List<AuthorResponse> getAuthors(){
        return authorService.getAuthors();
    }

    @PostMapping List<AuthorResponse> saveAuthors(@RequestBody AuthorRequest authorRequest){
        return authorService.saveAuthors(authorRequest);
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable Long id,@RequestBody AuthorRequest authorRequest){
        authorService.updateAuthor(id,authorRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
    }
}
