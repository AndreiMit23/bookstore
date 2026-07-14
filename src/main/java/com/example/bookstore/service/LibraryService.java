package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorResponse;
import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.dto.BookWithAuthorRequest;
import com.example.bookstore.dto.BookWithAuthorResponse;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Coordinates operations that span more than one resource, keeping the single-resource
 * services focused.
 */
@Service
public class LibraryService {

    private final AuthorService authorService;
    private final BookService bookService;

    public LibraryService(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    /**
     * Creates a new author and a new book in a single transaction, linking them via the
     * many-to-many. Both delegate calls join this transaction, so a failure in either one
     * rolls the whole operation back.
     */
    @Transactional
    public BookWithAuthorResponse createBookWithAuthor(BookWithAuthorRequest request) {
        AuthorResponse author = authorService.create(request.author());

        BookRequest source = request.book();
        Set<Long> authorIds = new LinkedHashSet<>();
        if (source.authorIds() != null) {
            authorIds.addAll(source.authorIds());
        }
        authorIds.add(author.id());
        BookRequest bookRequest = new BookRequest(source.title(), source.description(),
                source.publicationYear(), source.publisherId(), authorIds, source.profile());

        BookResponse book = bookService.create(bookRequest);

        return new BookWithAuthorResponse(book, authorService.findById(author.id()));
    }
}
