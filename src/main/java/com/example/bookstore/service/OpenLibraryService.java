package com.example.bookstore.service;

import com.example.bookstore.dto.CoverImages;
import com.example.bookstore.dto.ExternalBookDetail;
import com.example.bookstore.dto.ExternalBookSearchResponse;
import com.example.bookstore.dto.ExternalBookSummary;
import com.example.bookstore.exception.ExternalServiceException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/** Retrieves book data from the Open Library public API and maps it to our DTOs. */
@Service
public class OpenLibraryService {

    private final RestClient restClient;

    public OpenLibraryService(RestClient openLibraryRestClient) {
        this.restClient = openLibraryRestClient;
    }

    /** Full-text search; mirrors {@code GET /search.json?q=...}. */
    public ExternalBookSearchResponse search(String query) {
        SearchResult result;
        try {
            result = restClient.get()
                    .uri(uri -> uri.path("/search.json").queryParam("q", query).build())
                    .retrieve()
                    .body(SearchResult.class);
        } catch (RestClientException ex) {
            throw new ExternalServiceException("Open Library search failed", ex);
        }
        if (result == null || result.docs() == null) {
            return new ExternalBookSearchResponse(0, List.of());
        }
        List<ExternalBookSummary> results = result.docs().stream()
                .map(OpenLibraryService::toSummary)
                .toList();
        return new ExternalBookSearchResponse(result.numFound(), results);
    }

    /** Single-book lookup by ISBN; mirrors {@code GET /api/books?bibkeys=ISBN:...&jscmd=data}. */
    public ExternalBookDetail lookupByIsbn(String isbn) {
        String bibkey = "ISBN:" + isbn;
        Map<String, IsbnData> response;
        try {
            response = restClient.get()
                    .uri(uri -> uri.path("/api/books")
                            .queryParam("bibkeys", bibkey)
                            .queryParam("format", "json")
                            .queryParam("jscmd", "data")
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, IsbnData>>() {});
        } catch (RestClientException ex) {
            throw new ExternalServiceException("Open Library ISBN lookup failed", ex);
        }
        IsbnData data = response == null ? null : response.get(bibkey);
        if (data == null) {
            throw new ResourceNotFoundException("External book (ISBN)", isbn);
        }
        return toDetail(data, isbn);
    }

    private static ExternalBookSummary toSummary(SearchDoc doc) {
        String isbn = doc.isbn() == null || doc.isbn().isEmpty() ? null : doc.isbn().get(0);
        return new ExternalBookSummary(doc.title(), doc.authorNames(), doc.firstPublishYear(), isbn,
                coverFromId(doc.coverId()));
    }

    private static ExternalBookDetail toDetail(IsbnData data, String isbn) {
        List<String> authors = data.authors() == null ? List.of()
                : data.authors().stream().map(NamedRef::name).toList();
        List<String> publishers = data.publishers() == null ? List.of()
                : data.publishers().stream().map(NamedRef::name).toList();
        CoverImages cover = data.cover() == null ? null
                : new CoverImages(data.cover().small(), data.cover().medium(), data.cover().large());
        return new ExternalBookDetail(data.title(), authors, publishers,
                data.numberOfPages(), data.publishDate(), isbn, cover);
    }

    /** Search hits carry only a numeric cover id; build the three cover URLs from it. */
    private static CoverImages coverFromId(Integer coverId) {
        if (coverId == null) {
            return null;
        }
        String base = "https://covers.openlibrary.org/b/id/" + coverId + "-";
        return new CoverImages(base + "S.jpg", base + "M.jpg", base + "L.jpg");
    }

    // --- Internal records mirroring the Open Library JSON we consume ---

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record SearchResult(int numFound, List<SearchDoc> docs) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record SearchDoc(
            String title,
            @JsonProperty("author_name") List<String> authorNames,
            @JsonProperty("first_publish_year") Integer firstPublishYear,
            List<String> isbn,
            @JsonProperty("cover_i") Integer coverId) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record IsbnData(
            String title,
            List<NamedRef> authors,
            List<NamedRef> publishers,
            @JsonProperty("number_of_pages") Integer numberOfPages,
            @JsonProperty("publish_date") String publishDate,
            Cover cover) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record NamedRef(String name) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Cover(String small, String medium, String large) {
    }
}
