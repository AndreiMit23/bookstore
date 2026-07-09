package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookRequest;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookStoreService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookStoreService(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public List<Author> saveAuthors(List<Author> authorList){
        return authorRepository.saveAll(authorList);
    }

    public List<Book> saveBooks(List<Book> bookList){
        return bookRepository.saveAll(bookList);
    }

    public void updateAuthor(Long id, AuthorRequest authorRequest){
        Author author = authorRepository.findById(id).orElseThrow();

        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());

        authorRepository.save(author);
    }

    public void updateBook(Long id, BookRequest bookRequest){
        Book book = bookRepository.findById(id).orElseThrow();

        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setPublicationYear(bookRequest.getPublicationYear());

        bookRepository.save(book);
    }

    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
