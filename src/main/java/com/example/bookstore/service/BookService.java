package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Publisher;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.module_book.BookRequest;
import com.example.bookstore.module_book.BookResponse;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.bookMapper = bookMapper;
    }

    public BookResponse saveBooks(BookRequest bookRequest){
        Book book = new Book(bookRequest.getTitle(),bookRequest.getDescription(),bookRequest.getPublicationYear());


    }

    public List<BookResponse> getBooks(){
        List<Book> books = bookRepository.findAll();

        return bookMapper.toResponseList(books);
    }

    public List<BookResponse> saveBooks(BookRequest bookRequest){
        Book book = bookMapper.toEntity(bookRequest);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        List<Book> books = bookRepository.saveAll(bookList);

        return bookMapper.toResponseList(books);
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
