package com.example.bookstore.mapper;

import com.example.bookstore.dto.module_author.*;
import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper {
    private final BookMapper bookMapper;

    public AuthorMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public Author toEntity(AuthorRequest authorRequest){
        Author author = new Author(authorRequest.getFirstName(),authorRequest.getLastName());

        if(authorRequest.getAuthorProfile() != null){
            AuthorProfile authorProfile = toProfileEntity(authorRequest.getAuthorProfile());

            author.setAuthorProfile(authorProfile);
        }

        return author;
    }

    public AuthorResponse toResponse(Author author){
        AuthorProfileResponse authorProfileResponse = null;

        if(author.getAuthorProfile() != null){
            authorProfileResponse = toProfileResponse(author.getAuthorProfile());
        }
        //lombok - builder smecherie:D
        // SECRET: nu folosi lombok pe entitati
        return AuthorResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .authorProfile(authorProfileResponse)
                .books(bookMapper.toResponseList(new ArrayList<>(author.getBooks())))
                .build();
    }

    public AuthorProfile toProfileEntity(AuthorProfileRequest authorProfileRequest){
        return new AuthorProfile(authorProfileRequest.getBiography(),authorProfileRequest.getWebsite());
    }

    public AuthorProfileResponse toProfileResponse(AuthorProfile authorProfile){
       return AuthorProfileResponseBuilder.builder()
                .biography(authorProfile.getBiography())
                .website(authorProfile.getWebsite())
                .build();
    }

    public List<AuthorResponse> toResponseList(List<Author> authors){
        List<AuthorResponse> authorResponses = new ArrayList<>();

        for(Author author : authors){
            authorResponses.add(toResponse(author));
        }

        return authorResponses;
    }
}
