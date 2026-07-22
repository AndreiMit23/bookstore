package com.example.bookstore.controller;

import com.example.bookstore.dto.module_author.AuthorRequest;
import com.example.bookstore.dto.module_author.AuthorResponse;
import com.example.bookstore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }
    @GetMapping
    @Operation(summary = "This endpoint returns a list of authors", description = "It has the role to return all of the authors stored in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author was found"),
            @ApiResponse(responseCode = "404", description = "Author was not found")
    })
    public List<AuthorResponse> getAuthors(){
        return authorService.getAuthors();
    }

    @PostMapping
    @Operation(summary = "Save a list of authors", description = "Returns a list of authors from the request body, one or more books can be created")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Authors saved"),
            @ApiResponse(responseCode = "400", description = "Bad request for saving")
    })
    List<AuthorResponse> saveAuthors(@RequestBody AuthorRequest authorRequest){
        return authorService.saveAuthors(authorRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update author",description = "Updates an author via id endpoint and a request body")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated"),
            @ApiResponse(responseCode = "400", description = "Bad request, the author was not created")
    })
    public void updateAuthor(@PathVariable Long id,@RequestBody AuthorRequest authorRequest){
        authorService.updateAuthor(id,authorRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author", description = "Deletes a author via id endpoint")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author deleted"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
    }
}
