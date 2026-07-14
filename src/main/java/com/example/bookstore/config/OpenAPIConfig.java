package com.example.bookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI bookStoreOpenAPI(){
        return new OpenAPI().info(new Info().title("BookStore API").description("This is an application that has the role to do CRUD operations and relation endpoints with authors,books,publishers and library operations"));
    }
}
