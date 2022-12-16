package com.example.takehometest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class ServiceController {
  @GetMapping(value = "/search")
  public String search(@RequestParam String text) throws IOException, InterruptedException {
    System.out.println(text);
    HttpClient client = HttpClient.newHttpClient();
    String url = "http://openlibrary.org/search.json?q=" + text;
    URI uri = URI.create(url);
    HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

  @GetMapping(value = "/get")
  public String get(@RequestParam String id) throws IOException, InterruptedException {
    System.out.println(id);
    HttpClient client = HttpClient.newHttpClient();
    String url = "https://openlibrary.org/books/" + id + ".json";
    URI uri = URI.create(url);
    HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
