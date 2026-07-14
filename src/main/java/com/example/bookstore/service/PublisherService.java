package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Publisher;
import com.example.bookstore.mapper.PublisherMapper;
import com.example.bookstore.module_publisher.PublisherRequest;
import com.example.bookstore.module_publisher.PublisherResponse;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public PublisherService(PublisherRepository publisherRepository,BookRepository bookRepository, PublisherMapper publisherMapper){
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
        this.publisherMapper = publisherMapper;
    }

    public List<PublisherResponse> getPublishers(){
        List<Publisher> publishers = publisherRepository.findAll();
        return  publisherMapper.publisherResponseList(publishers);
    }

    public List<PublisherResponse> savePublishers(PublisherRequest publisherRequest){
        Publisher publisher = publisherMapper.toEntity(publisherRequest);

        publisher = publisherRepository.save(publisher);
        Book book = bookRepository.findById(publisherRequest.getBookId()).orElseThrow();
        book.setPublisher(publisher);

        bookRepository.save(book);

        List<Publisher> publishers = new ArrayList<>();
        publishers.add(publisher);

        return publisherMapper.publisherResponseList(publishers);
    }

    public void updatePublisher(Long ID, PublisherRequest publisherRequest){
        Publisher publisher = publisherRepository.findById(ID).orElseThrow();

        publisher.setCity(publisherRequest.getCity());
        publisher.setName(publisherRequest.getName());

        publisherRepository.save(publisher);
    }

    public void deletePublisher(Long ID){
        publisherRepository.deleteById(ID);
    }

}
