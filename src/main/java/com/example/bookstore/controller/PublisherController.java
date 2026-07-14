package com.example.bookstore.controller;

import com.example.bookstore.module_publisher.PublisherRequest;
import com.example.bookstore.module_publisher.PublisherResponse;
import com.example.bookstore.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    public PublisherController(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    @PostMapping
    public List<PublisherResponse> savePublishers(@RequestBody PublisherRequest publisherRequest){
        return publisherService.savePublishers(publisherRequest);
    }

    @GetMapping
    public List<PublisherResponse> getPublishers(){
        return publisherService.getPublishers();
    }

    @PutMapping("/{ID}")
    public void updatePublisher(@PathVariable Long ID,@RequestBody PublisherRequest publisherRequest){
        publisherService.updatePublisher(ID,publisherRequest);
    }

    @DeleteMapping("/{ID}")
    public void deletePublisher(@PathVariable Long ID){
        publisherService.deletePublisher(ID);
    }
}
