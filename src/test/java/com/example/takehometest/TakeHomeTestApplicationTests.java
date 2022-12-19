package com.example.takehometest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TakeHomeTestApplicationTests {
  private final String BASE_URL = "https://openlibrary.org";
  private final String SEARCH_PATH = "/search.json?q=";
  private final String BOOKS_PATH = "/books/";
  @InjectMocks
  ServiceController serviceController;

  @Test
  void testingSearchWithValidResults() {
    try {
      String text = "lord+of+the+rings";
      String url = SEARCH_PATH + text;
      String response = serviceController.processRequest(url);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readValue(response, JsonNode.class);
      JsonNode numFoundNode = node.get("numFound");
      String expected = numFoundNode.asText();

      int numFound = Integer.parseInt(expected);
      assertTrue(numFound > 0);
    } catch (Exception e) {
      fail(e);
    }
  }
}
