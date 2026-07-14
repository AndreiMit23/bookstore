package com.example.bookstore.service;

import com.example.bookstore.dto.AuthorProfileDto;
import com.example.bookstore.dto.AuthorRequest;
import com.example.bookstore.dto.AuthorResponse;
import com.example.bookstore.dto.BookSummary;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorProfile;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.AuthorRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<AuthorResponse> findAll() {
        return authorRepository.findAll().stream().map(EntityMapper::toAuthorResponse).toList();
    }

    @Transactional(readOnly = true)
    public AuthorResponse findById(Long id) {
        return EntityMapper.toAuthorResponse(getOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<BookSummary> findBooks(Long id) {
        return getOrThrow(id).getBooks().stream().map(EntityMapper::toBookSummary).toList();
    }

    public AuthorResponse create(AuthorRequest request) {
        Author author = new Author(request.firstName(), request.lastName());
        if (request.profile() != null) {
            author.setAuthorProfile(new AuthorProfile(request.profile().biography(), request.profile().website()));
        }
        return EntityMapper.toAuthorResponse(authorRepository.save(author));
    }

    /**
     * DEMO ONLY: binds the {@link Author} entity straight from the request body instead of
     * going through {@link AuthorRequest}. Kept beside {@link #create} to contrast the two.
     * Not recommended: no bean validation, schema coupling, over-posting risk.
     */
    public AuthorResponse createFromEntity(Author author) {
        return EntityMapper.toAuthorResponse(authorRepository.save(author));
    }

    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = getOrThrow(id);
        author.setFirstName(request.firstName());
        author.setLastName(request.lastName());
        if (request.profile() != null) {
            applyProfile(author, request.profile());
        }
        return EntityMapper.toAuthorResponse(author);
    }

    public void delete(Long id) {
        Author author = getOrThrow(id);
        // Break the many-to-many links (Book owns the join table) before removing the author.
        for (Book book : Set.copyOf(author.getBooks())) {
            book.removeAuthor(author);
        }
        authorRepository.delete(author); // cascade removes the profile too
    }

    /** Sets, updates, or (with a null body) clears the one-to-one profile. */
    public AuthorResponse setProfile(Long id, AuthorProfileDto dto) {
        Author author = getOrThrow(id);
        if (dto == null) {
            author.setAuthorProfile(null);
        } else {
            applyProfile(author, dto);
        }
        return EntityMapper.toAuthorResponse(author);
    }

    private void applyProfile(Author author, AuthorProfileDto dto) {
        AuthorProfile profile = author.getAuthorProfile();
        if (profile == null) {
            author.setAuthorProfile(new AuthorProfile(dto.biography(), dto.website()));
        } else {
            profile.setBiography(dto.biography());
            profile.setWebsite(dto.website());
        }
    }

    private Author getOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", id));
    }
}
