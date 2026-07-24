package com.example.bookstore.service;

import com.example.bookstore.dto.module_book.ExternalBookResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookProfile;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.dto.module_book.BookRequest;
import com.example.bookstore.dto.module_book.BookResponse;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BookMapper bookMapper;
    private final OpenLibraryService openLibraryService;

    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository, BookMapper bookMapper, OpenLibraryService openLibraryService){
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.bookMapper = bookMapper;
        this.openLibraryService = openLibraryService;
    }

    public BookResponse toResponse(Book book){
        return bookMapper.toResponse(book);
    }

    public BookResponse getBookByIsbn(String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);

        if(book.isPresent()){
            return bookMapper.toResponse(book.get());
        }

        ExternalBookResponse externalBookResponse = openLibraryService.searchBookByIsbn(isbn);

        Book newBook = new Book(externalBookResponse.getTitle(),externalBookResponse.getDescription(),externalBookResponse.getPublicationYear());

        newBook.setIsbn(isbn);

        Book savedBook = bookRepository.save(newBook);

        return bookMapper.toResponse(savedBook);
    }


    public Book saveBook(BookRequest bookRequest){
        Book book = new Book(bookRequest.getTitle(),bookRequest.getDescription(),bookRequest.getPublicationYear());

        if(bookRequest.getBookProfile() != null){
            book.setBookProfile(new BookProfile(bookRequest.getBookProfile().getGenre(),bookRequest.getBookProfile().getPages(),bookRequest.getBookProfile().getLanguage()));
        }

        return bookRepository.save(book);
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
