package com.example.bookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void seedDataExposesAllRelationships() throws Exception {
        // Many-to-many: "Good Omens" is co-authored, so it lists two authors.
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(Matchers.greaterThanOrEqualTo(5)))
                .andExpect(jsonPath("$[?(@.title == 'Good Omens')].authors.length()").value(2));
    }

    @Test
    void publisherCrudLifecycle() throws Exception {
        Long id = createPublisher("Tor Books", "New York");

        mockMvc.perform(get("/api/publishers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tor Books"));

        mockMvc.perform(put("/api/publishers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Tor\",\"city\":\"NYC\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("NYC"));

        mockMvc.perform(delete("/api/publishers/{id}", id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/publishers/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void bookManyToManyLinkAndUnlink() throws Exception {
        Long publisherId = createPublisher("Vintage", "London");
        Long authorA = createAuthor("Ada", "Lovelace");
        Long authorB = createAuthor("Alan", "Turing");

        String body = "{\"title\":\"Computing\",\"publisherId\":" + publisherId
                + ",\"authorIds\":[" + authorA + "]}";
        Long bookId = idOf(mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.publisher.name").value("Vintage"))
                .andExpect(jsonPath("$.authors.length()").value(1)));

        mockMvc.perform(post("/api/books/{id}/authors/{authorId}", bookId, authorB))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authors.length()").value(2));

        mockMvc.perform(delete("/api/books/{id}/authors/{authorId}", bookId, authorA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authors.length()").value(1));
    }

    @Test
    void bookOneToOneProfile() throws Exception {
        Long publisherId = createPublisher("Manning", "Shelter Island");

        String body = "{\"title\":\"Spring in Action\",\"publisherId\":" + publisherId
                + ",\"profile\":{\"genre\":\"Technical\",\"pages\":520,\"language\":\"English\"}}";
        Long bookId = idOf(mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.profile.pages").value(520)));

        mockMvc.perform(put("/api/books/{id}/profile", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"genre\":\"Technical\",\"pages\":540,\"language\":\"English\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.pages").value(540));

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.pages").value(540));
    }

    @Test
    void authorOneToOneProfile() throws Exception {
        String body = "{\"firstName\":\"Grace\",\"lastName\":\"Hopper\","
                + "\"profile\":{\"biography\":\"Computing pioneer\",\"website\":\"https://example.com\"}}";
        Long id = idOf(mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()));

        mockMvc.perform(get("/api/authors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.biography").value("Computing pioneer"));
    }

    @Test
    void createBookAndAuthorTogether() throws Exception {
        Long publisherId = createPublisher("Addison-Wesley", "Boston");

        String body = "{"
                + "\"author\":{\"firstName\":\"Kent\",\"lastName\":\"Beck\"},"
                + "\"book\":{\"title\":\"TDD by Example\",\"publisherId\":" + publisherId + "}"
                + "}";

        mockMvc.perform(post("/api/library/book-with-author")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.book.title").value("TDD by Example"))
                .andExpect(jsonPath("$.book.authors[0].lastName").value("Beck"))
                .andExpect(jsonPath("$.author.books[0].title").value("TDD by Example"));
    }

    @Test
    void createBookAndAuthorTogetherIsAtomic() throws Exception {
        String body = "{"
                + "\"author\":{\"firstName\":\"Ghost\",\"lastName\":\"Writer\"},"
                + "\"book\":{\"title\":\"Never Saved\",\"publisherId\":999999}"
                + "}";

        mockMvc.perform(post("/api/library/book-with-author")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.lastName == 'Writer' && @.firstName == 'Ghost')]").isEmpty());
    }

    @Test
    void missingResourceReturns404() throws Exception {
        mockMvc.perform(get("/api/authors/{id}", 999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void invalidPayloadReturns400() throws Exception {
        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\",\"lastName\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.firstName").exists());
    }

    private Long createPublisher(String name, String city) throws Exception {
        String body = "{\"name\":\"" + name + "\",\"city\":\"" + city + "\"}";
        return idOf(mockMvc.perform(post("/api/publishers")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()));
    }

    private Long createAuthor(String firstName, String lastName) throws Exception {
        String body = "{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\"}";
        return idOf(mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()));
    }

    private Long idOf(ResultActions created) throws Exception {
        String location = created.andReturn().getResponse().getHeader("Location");
        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }
}
