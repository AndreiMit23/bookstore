package com.example.bookstore.controller;

import com.example.bookstore.dto.module_publisher.PublisherRequest;
import com.example.bookstore.dto.module_publisher.PublisherResponse;
import com.example.bookstore.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Save a list of publishers", description = "Returns a list of publishers from the request body, one or more books can be created")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Publisher saved"),
            @ApiResponse(responseCode = "400", description = "Bad request for saving")
    })
    public List<PublisherResponse> savePublishers(@RequestBody PublisherRequest publisherRequest){
        return publisherService.savePublishers(publisherRequest);
    }

    @GetMapping
    @Operation(summary = "This endpoint returns a list of publishers", description = "It has the role to return all of the publishers stored in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publisher was found"),
            @ApiResponse(responseCode = "404", description = "Publisher was not found")
    })
    public List<PublisherResponse> getPublishers(){
        return publisherService.getPublishers();
    }

    @PutMapping("/{ID}")
    @Operation(summary = "Update publisher",description = "Updates an publisher via id endpoint and a request body")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publisher updated"),
            @ApiResponse(responseCode = "400", description = "Bad request, the publisher was not created")
    })
    public void updatePublisher(@PathVariable Long ID,@RequestBody PublisherRequest publisherRequest){
        publisherService.updatePublisher(ID,publisherRequest);
    }


    @DeleteMapping("/{ID}")
    @Operation(summary = "Delete publisher", description = "Deletes a publisher via id endpoint")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publisher deleted"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")
    })
    public void deletePublisher(@PathVariable Long ID){
        publisherService.deletePublisher(ID);
    }
}
