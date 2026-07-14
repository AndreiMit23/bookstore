package com.example.bookstore.service;

import com.example.bookstore.dto.BookProfileDto;
import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookProfile;
import com.example.bookstore.entity.Publisher;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
            PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(EntityMapper::toBookResponse).toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        return EntityMapper.toBookResponse(getOrThrow(id));
    }

    public BookResponse create(BookRequest request) {
        Book book = new Book(request.title(), request.description(), request.publicationYear());
        book.setPublisher(resolvePublisher(request.publisherId()));
        if (request.authorIds() != null) {
            for (Long authorId : request.authorIds()) {
                book.addAuthor(resolveAuthor(authorId));
            }
        }
        if (request.profile() != null) {
            book.setBookProfile(toProfile(request.profile()));
        }
        return EntityMapper.toBookResponse(bookRepository.save(book));
    }

    public BookResponse update(Long id, BookRequest request) {
        Book book = getOrThrow(id);
        book.setTitle(request.title());
        book.setDescription(request.description());
        book.setPublicationYear(request.publicationYear());
        book.setPublisher(resolvePublisher(request.publisherId()));
        if (request.authorIds() != null) {
            replaceAuthors(book, request.authorIds());
        }
        if (request.profile() != null) {
            applyProfile(book, request.profile());
        }
        return EntityMapper.toBookResponse(book);
    }

    public void delete(Long id) {
        // Book owns the many-to-many join table, so deleting it removes the join rows;
        // the one-to-one BookProfile is removed via cascade/orphanRemoval.
        bookRepository.delete(getOrThrow(id));
    }

    public BookResponse addAuthor(Long bookId, Long authorId) {
        Book book = getOrThrow(bookId);
        book.addAuthor(resolveAuthor(authorId));
        return EntityMapper.toBookResponse(book);
    }

    public BookResponse removeAuthor(Long bookId, Long authorId) {
        Book book = getOrThrow(bookId);
        book.removeAuthor(resolveAuthor(authorId));
        return EntityMapper.toBookResponse(book);
    }

    /** Sets, updates, or (with a null body) clears the one-to-one profile. */
    public BookResponse setProfile(Long id, BookProfileDto dto) {
        Book book = getOrThrow(id);
        if (dto == null) {
            book.setBookProfile(null);
        } else {
            applyProfile(book, dto);
        }
        return EntityMapper.toBookResponse(book);
    }

    private void replaceAuthors(Book book, Set<Long> authorIds) {
        for (Author existing : Set.copyOf(book.getAuthors())) {
            if (!authorIds.contains(existing.getId())) {
                book.removeAuthor(existing);
            }
        }
        for (Long authorId : authorIds) {
            book.addAuthor(resolveAuthor(authorId));
        }
    }

    private void applyProfile(Book book, BookProfileDto dto) {
        BookProfile profile = book.getBookProfile();
        if (profile == null) {
            book.setBookProfile(toProfile(dto));
        } else {
            profile.setGenre(dto.genre());
            profile.setPages(dto.pages());
            profile.setLanguage(dto.language());
        }
    }

    private BookProfile toProfile(BookProfileDto dto) {
        return new BookProfile(dto.genre(), dto.pages(), dto.language());
    }

    private Publisher resolvePublisher(Long publisherId) {
        if (publisherId == null) {
            return null;
        }
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", publisherId));
    }

    private Author resolveAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", authorId));
    }

    private Book getOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }
}
