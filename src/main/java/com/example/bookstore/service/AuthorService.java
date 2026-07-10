package com.example.bookstore.service;

import com.example.bookstore.entity.Author;
import com.example.bookstore.entity.AuthorRequest;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public List<Author> saveAuthors(List<Author> authorList){
        return authorRepository.saveAll(authorList);
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
