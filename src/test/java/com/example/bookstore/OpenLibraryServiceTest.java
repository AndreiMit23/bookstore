package com.example.bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.example.bookstore.dto.ExternalBookDetail;
import com.example.bookstore.dto.ExternalBookSearchResponse;
import com.example.bookstore.exception.ExternalServiceException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.service.OpenLibraryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class OpenLibraryServiceTest {

    private MockRestServiceServer server;
    private OpenLibraryService service;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("https://openlibrary.org");
        server = MockRestServiceServer.bindTo(builder).build();
        service = new OpenLibraryService(builder.build());
    }

    @Test
    void searchMapsDocsToSummaries() {
        String body = """
                {
                  "numFound": 2,
                  "docs": [
                    {"title": "Dune", "author_name": ["Frank Herbert"], "first_publish_year": 1965,
                     "isbn": ["9780441172719", "0441172717"], "cover_i": 11481354},
                    {"title": "Dune Messiah", "author_name": ["Frank Herbert"], "first_publish_year": 1969,
                     "isbn": ["9780593098233"]}
                  ]
                }
                """;
        server.expect(requestTo(Matchers.containsString("/search.json?q=dune")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        ExternalBookSearchResponse response = service.search("dune");

        server.verify();
        assertEquals(2, response.numFound());
        assertEquals(2, response.results().size());
        assertEquals("Dune", response.results().get(0).title());
        assertEquals("Frank Herbert", response.results().get(0).authors().get(0));
        assertEquals(1965, response.results().get(0).firstPublishYear());
        assertEquals("9780441172719", response.results().get(0).isbn());
        // cover_i -> constructed cover URLs; a doc without cover_i has a null cover.
        assertEquals("https://covers.openlibrary.org/b/id/11481354-M.jpg",
                response.results().get(0).cover().medium());
        assertNull(response.results().get(1).cover());
    }

    @Test
    void lookupByIsbnMapsDetail() {
        String body = """
                {
                  "ISBN:9780441172719": {
                    "title": "Dune",
                    "authors": [{"name": "Frank Herbert"}],
                    "publishers": [{"name": "Ace Books"}],
                    "number_of_pages": 412,
                    "publish_date": "1990",
                    "cover": {
                      "small": "https://covers.openlibrary.org/b/id/15110282-S.jpg",
                      "medium": "https://covers.openlibrary.org/b/id/15110282-M.jpg",
                      "large": "https://covers.openlibrary.org/b/id/15110282-L.jpg"
                    }
                  }
                }
                """;
        server.expect(requestTo(Matchers.containsString("/api/books")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(body, MediaType.APPLICATION_JSON));

        ExternalBookDetail detail = service.lookupByIsbn("9780441172719");

        server.verify();
        assertEquals("Dune", detail.title());
        assertEquals("Frank Herbert", detail.authors().get(0));
        assertEquals("Ace Books", detail.publishers().get(0));
        assertEquals(412, detail.numberOfPages());
        assertEquals("1990", detail.publishDate());
        assertEquals("9780441172719", detail.isbn());
        assertEquals("https://covers.openlibrary.org/b/id/15110282-L.jpg", detail.cover().large());
    }

    @Test
    void lookupByUnknownIsbnThrowsNotFound() {
        server.expect(requestTo(Matchers.containsString("/api/books")))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        assertThrows(ResourceNotFoundException.class, () -> service.lookupByIsbn("0000000000"));
        server.verify();
    }

    @Test
    void upstreamFailureThrowsExternalServiceException() {
        server.expect(requestTo(Matchers.containsString("/search.json")))
                .andRespond(withServerError());

        assertThrows(ExternalServiceException.class, () -> service.search("dune"));
        server.verify();
    }
}
