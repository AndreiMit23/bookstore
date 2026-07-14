package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorProfileDto;
import com.example.bookstore.dto.AuthorResponse;
import com.example.bookstore.dto.AuthorSummary;
import com.example.bookstore.dto.BookProfileDto;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.dto.BookSummary;
import com.example.bookstore.dto.PublisherResponse;
import com.example.bookstore.dto.PublisherSummary;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorProfile;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookProfile;
import com.example.bookstore.entity.Publisher;
import java.util.List;

/**
 * Converts JPA entities into DTOs. Centralising this keeps serialization out of the
 * entities and breaks the bidirectional relationship cycles that would otherwise
 * produce infinite JSON (author -> books -> authors -> ...).
 */
final class EntityMapper {

    private EntityMapper() {
    }

    static AuthorResponse toAuthorResponse(Author author) {
        AuthorProfile profile = author.getAuthorProfile();
        AuthorProfileDto profileDto =
                profile == null ? null : new AuthorProfileDto(profile.getBiography(), profile.getWebsite());
        List<BookSummary> books = author.getBooks().stream().map(EntityMapper::toBookSummary).toList();
        return new AuthorResponse(author.getId(), author.getFirstName(), author.getLastName(),
                profileDto, books);
    }

    static BookResponse toBookResponse(Book book) {
        PublisherSummary publisher = book.getPublisher() == null
                ? null
                : new PublisherSummary(book.getPublisher().getId(), book.getPublisher().getName());
        List<AuthorSummary> authors = book.getAuthors().stream().map(EntityMapper::toAuthorSummary).toList();
        BookProfile bp = book.getBookProfile();
        BookProfileDto profileDto =
                bp == null ? null : new BookProfileDto(bp.getGenre(), bp.getPages(), bp.getLanguage());
        return new BookResponse(book.getId(), book.getTitle(), book.getDescription(),
                book.getPublicationYear(), publisher, authors, profileDto);
    }

    static PublisherResponse toPublisherResponse(Publisher publisher, List<Book> books) {
        List<BookSummary> summaries = books.stream().map(EntityMapper::toBookSummary).toList();
        return new PublisherResponse(publisher.getId(), publisher.getName(), publisher.getCity(), summaries);
    }

    static AuthorSummary toAuthorSummary(Author author) {
        return new AuthorSummary(author.getId(), author.getFirstName(), author.getLastName());
    }

    static BookSummary toBookSummary(Book book) {
        return new BookSummary(book.getId(), book.getTitle());
    }
}
