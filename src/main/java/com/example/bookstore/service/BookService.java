package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookRequest;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public List<Book> saveBooks(List<Book> bookList){
        return bookRepository.saveAll(bookList);
    }

    public void updateBook(Long id, BookRequest bookRequest){
        Book book = bookRepository.findById(id).orElseThrow();

        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setPublicationYear(bookRequest.getPublicationYear());

        bookRepository.save(book);
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
