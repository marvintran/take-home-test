package com.example.takehometest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class ServiceController {
  @Value("${external.url.openlibrary}")
  private String baseUrl;
  @Value("${external.url.openlibrary.search}")
  private String searchPath;
  @Value("${external.url.openlibrary.books}")
  private String booksPath;

  @GetMapping(value = "/search")
  public String search(@RequestParam String text) {
    String textProcessed = text.replaceAll(" ", "+");
    String url = searchPath + textProcessed;
    return processRequest(url);
  }

  @GetMapping(value = "/get")
  public String get(@RequestParam String id) {
    String idProcessed = id.trim().replaceAll(" ", "_");
    String url = booksPath + idProcessed + ".json";
    return processRequest(url);
  }

  private String processRequest(String url) {
    HttpClient client = HttpClient.newHttpClient();
    URI uri = URI.create(baseUrl + url);
    HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      String json = response.body();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readValue(json, JsonNode.class);

      JsonNode errorNode = node.get("error");
      JsonNode keyNode = node.get("key");

      if (errorNode != null) {
        String errorValue = errorNode.asText();
        String keyValue = keyNode.asText().replace(booksPath, "");

        ObjectNode toReturn = mapper.createObjectNode();
        toReturn.put("error", errorValue);
        toReturn.put("key", keyValue);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(toReturn);
      }

      return response.body();

    } catch (IOException e) {
      return "{\"error\": \"Couldn't execute the request correctly due to data processing error\"}";
    } catch (InterruptedException e) {
      return "{\"error\": \"The request was interrupted\"}";
    }
  }
}
