package com.example.bookstore.mapper;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorProfile;
import com.example.bookstore.module_author.AuthorProfileRequest;
import com.example.bookstore.module_author.AuthorProfileResponse;
import com.example.bookstore.module_author.AuthorRequest;
import com.example.bookstore.module_author.AuthorResponse;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper {
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

        return new AuthorResponse(author.getId(),author.getFirstName(),author.getLastName(), authorProfileResponse);
    }

    public AuthorProfile toProfileEntity(AuthorProfileRequest authorProfileRequest){
        return new AuthorProfile(authorProfileRequest.getBiography(),authorProfileRequest.getWebsite());
    }

    public AuthorProfileResponse toProfileResponse(AuthorProfile authorProfile){
        return new AuthorProfileResponse(authorProfile.getBiography(),authorProfile.getWebsite());
    }

    public List<AuthorResponse> toResponseList(List<Author> authors){
        List<AuthorResponse> authorResponses = new ArrayList<>();

        for(Author author : authors){
            authorResponses.add(toResponse(author));
        }

        return authorResponses;
    }
}
