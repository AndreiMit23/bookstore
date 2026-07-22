package com.example.bookstore.service;

import com.example.bookstore.mapper.IsbnData;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenLibraryService {
    private final RestClient restClient;

    public OpenLibraryService(RestClient restClient){
        this.restClient = restClient;
    }

    public IsbnData searchByIsbn(String isbn){
        // Unlike /isbn/{isbn} (which 302-redirects to /books/{olid}), this endpoint returns 200
        // directly with a JSON object keyed by the bibkey, e.g. {"ISBN:0201558025": { ... }}.
        String bibkey = "ISBN:" + isbn;
        Map<String, IsbnData> response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books")
                        .queryParam("bibkeys", bibkey)
                        .queryParam("format", "json")
                        .queryParam("jscmd", "data")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, IsbnData>>() {});
        return response == null ? null : response.get(bibkey);
    }
}
