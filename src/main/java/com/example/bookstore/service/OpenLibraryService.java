package com.example.bookstore.service;

import com.example.bookstore.config.RestClientConfig;
import com.example.bookstore.mapper.IsbnData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenLibraryService {
    private final RestClient restClient;

    public OpenLibraryService(RestClient restClient){
        this.restClient = restClient;
    }

    public IsbnData searchByIsbn(String isbn){
       return restClient.get().uri("/isbn/"+isbn).retrieve().body(IsbnData.class);
    }
}
