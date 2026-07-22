package com.example.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/** Builds the {@link RestClient} used to call the Open Library public API. */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient openLibraryRestClient(@Value("${openlibrary.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
