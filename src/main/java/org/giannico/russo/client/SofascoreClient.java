package org.giannico.russo.client;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class SofascoreClient {
    private final HttpClient httpClient;

    public SofascoreClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String fetchTennisData(String endpoint) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://www.sofascore.com/" + endpoint))
                    .header("User-Agent", "Mozilla/5.0 (compatible)")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nella chiamata API", e);
        }
    }
}
