package com.example.bookstore.controller;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookRequest;
import com.example.bookstore.service.BookStoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookStoreController {
    private final BookStoreService bookStoreService;

    public BookStoreController(BookStoreService bookStoreService){
        this.bookStoreService = bookStoreService;
    }

    @GetMapping("/getAuthors")
    public List<Author> getAuthors(){
        return bookStoreService.getAuthors();
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks(){
        return bookStoreService.getBooks();
    }

    @PostMapping("/saveAuthors") List<Author> saveAuthors(@RequestBody List<Author> authorList){
        return bookStoreService.saveAuthors(authorList);
    }

    @PostMapping("/saveBooks")
    public List<Book> saveBooks(@RequestBody List<Book> bookList){
        return bookStoreService.saveBooks(bookList);
    }

    @PutMapping("/updateAuthor/{id}")
    public void updateAuthor(@PathVariable Long id,@RequestBody AuthorRequest authorRequest){
        bookStoreService.updateAuthor(id,authorRequest);
    }

    @PutMapping("/updateBook/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest){
        bookStoreService.updateBook(id,bookRequest);
    }

    @DeleteMapping("/deleteAuthor/{id}")
    public void deleteAuthor(@PathVariable Long id){
        bookStoreService.deleteAuthor(id);
    }

    @DeleteMapping("/deleteBook/{id}")
    public void deleteBook(@PathVariable Long id){
        bookStoreService.deleteBook(id);
    }
}
