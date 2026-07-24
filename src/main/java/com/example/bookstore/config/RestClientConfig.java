package com.example.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

//TODO: la DTO uri sa le fac cate un builder si sa instantiez DTO urile cu builder ul nu cu new
@Configuration
public class RestClientConfig {
    @Bean
    public RestClient openLibraryRestClient(){
        return RestClient.builder().baseUrl("https://openlibrary.org").build();
    }

    @Bean
    public RestClient openLibraryRedirectRestClient(){
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .baseUrl("https://openlibrary.org")
                .build();
    }
}
