package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Book searchBookByTitle(String title) throws Exception {
        String url = "https://gutendex.com/books?search=" + title;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode firstResult = root.get("results").get(0);

        Book book = objectMapper.treeToValue(firstResult, Book.class);
        JsonNode authorNode = firstResult.get("authors").get(0);
        Author author = objectMapper.treeToValue(authorNode, Author.class);

        book.setAuthor(author);
        return book;
    }
}

