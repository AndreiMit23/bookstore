package com.example.bookstore.service;

import com.example.bookstore.dto.module_author.AuthorRequest;
import com.example.bookstore.dto.module_author_book.AuthorAndBookRequest;
import com.example.bookstore.dto.module_book.BookRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CsvService {

    public final LibraryService libraryService;

    public CsvService(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    public void importCsv(String fileName){
        Path path = Paths.get("B:\\fisiereSpring",fileName);

        try(BufferedReader bufferedReader = Files.newBufferedReader(path)){

            String line;

            bufferedReader.readLine(); //NO header:) -- prima linie care are coloanele...

            while((line = bufferedReader.readLine()) != null){
                String[] data = line.split(",");

                AuthorAndBookRequest authorAndBookRequest = getAuthorAndBookRequest(data);

                libraryService.saveBookWithAuthor(authorAndBookRequest);
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static @NonNull AuthorAndBookRequest getAuthorAndBookRequest(String[] data) {
        String firstName = data[0].trim();
        String lastName = data[1].trim();

        String title = data[2].trim();
        String description = data[3].trim();

        Integer publicationYear = Integer.parseInt(data[4].trim());

        String isbn = data[5].trim();

        AuthorRequest authorRequest = new AuthorRequest(firstName,lastName);

        BookRequest bookRequest = new BookRequest(title,description,publicationYear);
        bookRequest.setIsbn(isbn);

        return new AuthorAndBookRequest(authorRequest,bookRequest);
    }
}

//TODO: fa sa primeasca si authorProfile si bookProfile
