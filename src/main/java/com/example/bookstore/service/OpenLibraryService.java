package com.example.bookstore.service;

import com.example.bookstore.mapper.IsbnData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenLibraryService {
    private final RestClient openLibraryRedirectRestClient;

    public OpenLibraryService(RestClient openLibraryRedirectRestClient){
        this.openLibraryRedirectRestClient = openLibraryRedirectRestClient;
    }

    public IsbnData searchByIsbn(String isbn){
       return openLibraryRedirectRestClient.get().uri("/isbn/"+isbn+".json").retrieve().body(IsbnData.class);
    }
}
