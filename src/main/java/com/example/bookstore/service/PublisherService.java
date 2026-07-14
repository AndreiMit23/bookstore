package com.example.bookstore.service;

import com.example.bookstore.dto.PublisherRequest;
import com.example.bookstore.dto.PublisherResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Publisher;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<PublisherResponse> findAll() {
        return publisherRepository.findAll().stream()
                .map(p -> EntityMapper.toPublisherResponse(p, bookRepository.findByPublisherId(p.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public PublisherResponse findById(Long id) {
        Publisher publisher = getOrThrow(id);
        return EntityMapper.toPublisherResponse(publisher, bookRepository.findByPublisherId(id));
    }

    public PublisherResponse create(PublisherRequest request) {
        Publisher publisher = publisherRepository.save(new Publisher(request.name(), request.city()));
        return EntityMapper.toPublisherResponse(publisher, List.of());
    }

    public PublisherResponse update(Long id, PublisherRequest request) {
        Publisher publisher = getOrThrow(id);
        publisher.setName(request.name());
        publisher.setCity(request.city());
        return EntityMapper.toPublisherResponse(publisher, bookRepository.findByPublisherId(id));
    }

    public void delete(Long id) {
        Publisher publisher = getOrThrow(id);
        // Detach books first so the many-to-one FK does not block deletion.
        for (Book book : bookRepository.findByPublisherId(id)) {
            book.setPublisher(null);
        }
        publisherRepository.delete(publisher);
    }

    private Publisher getOrThrow(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", id));
    }
}
