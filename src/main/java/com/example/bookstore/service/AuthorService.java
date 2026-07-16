package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorProfile;
import com.example.bookstore.mapper.AuthorMapper;
import com.example.bookstore.module_author.AuthorRequest;
import com.example.bookstore.module_author.AuthorResponse;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorResponse> getAuthors(){
        List<Author> authors = authorRepository.findAll();

        return authorMapper.toResponseList(authors);
    }

    public AuthorResponse saveAuthor(AuthorRequest authorRequest){
        Author author = new Author(authorRequest.getFirstName(),authorRequest.getLastName());

        if(authorRequest.getAuthorProfile() != null){
            author.setAuthorProfile(new AuthorProfile(authorRequest.getAuthorProfile().getBiography(), authorRequest.getAuthorProfile().getWebsite()));
        }

        authorRepository.save(author);

        return authorMapper.toResponse(author);
    }

    public List<AuthorResponse> saveAuthors(AuthorRequest authorRequest){
        Author author = authorMapper.toEntity(authorRequest);

        List<Author> authorList = new ArrayList<>();

        authorList.add(author);

        List<Author> authors = authorRepository.saveAll(authorList);

        return authorMapper.toResponseList(authors);
    }

    public void updateAuthor(Long id, AuthorRequest authorRequest){
        Author author = authorRepository.findById(id).orElseThrow();

        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());

        authorRepository.save(author);
    }

    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }
}
