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

  @Test
  void testingSearchWithEmptyStringNothingFound() {
    try {
      String text = "";
      String url = SEARCH_PATH + text;
      String response = serviceController.processRequest(url);

      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readValue(response, JsonNode.class);
      JsonNode numFoundNode = node.get("numFound");
      String expected = numFoundNode.asText();

      int numFound = Integer.parseInt(expected);
      assertTrue(numFound == 0);
    } catch (Exception e) {
      fail(e);
    }
  }
  
  @Test
  void testingGettingBookWithValidID() {
    try {
      String id = "OL9158246M";
      String url = BOOKS_PATH + id + ".json";
      String response = serviceController.processRequest(url);

      String expected = "{\"isbn_13\": [\"9788533619623\"], \"subtitle\": \"Trilogia, O\", \"source_records\": [\"amazon:8533619626\"], \"title\": \"Senhor dos Ane\\u00eds\", \"identifiers\": {\"librarything\": [\"1386651\"]}, \"covers\": [8740681], \"created\": {\"type\": \"/type/datetime\", \"value\": \"2008-04-30T09:38:13.731961\"}, \"physical_format\": \"Paperback\", \"isbn_10\": [\"8533619626\"], \"latest_revision\": 4, \"key\": \"/books/OL9158246M\", \"last_modified\": {\"type\": \"/type/datetime\", \"value\": \"2019-11-30T16:12:02.949830\"}, \"classifications\": {}, \"works\": [{\"key\": \"/works/OL27448W\"}], \"type\": {\"key\": \"/type/edition\"}, \"revision\": 4}";
      assertEquals(response, expected);
    } catch (Exception e) {
      fail(e);
    }
  }
}
