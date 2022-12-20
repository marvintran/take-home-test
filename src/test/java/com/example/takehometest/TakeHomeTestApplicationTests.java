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
  @InjectMocks
  ServiceController serviceController;

  @Test
  void testingSearchWithValidResults() {
    try {
      String text = "lord+of+the+rings";
      String response = serviceController.search(text);

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
      String response = serviceController.search(text);

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
      String response = serviceController.get(id);

      String expected = "{\"isbn_13\": [\"9788533619623\"], \"subtitle\": \"Trilogia, O\", \"source_records\": [\"amazon:8533619626\"], \"title\": \"Senhor dos Ane\\u00eds\", \"identifiers\": {\"librarything\": [\"1386651\"]}, \"covers\": [8740681], \"created\": {\"type\": \"/type/datetime\", \"value\": \"2008-04-30T09:38:13.731961\"}, \"physical_format\": \"Paperback\", \"isbn_10\": [\"8533619626\"], \"latest_revision\": 4, \"key\": \"/books/OL9158246M\", \"last_modified\": {\"type\": \"/type/datetime\", \"value\": \"2019-11-30T16:12:02.949830\"}, \"classifications\": {}, \"works\": [{\"key\": \"/works/OL27448W\"}], \"type\": {\"key\": \"/type/edition\"}, \"revision\": 4}";
      assertEquals(response, expected);
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  void testingGettingBookWithInvalidID() {
    try {
      String id = "abc123";
      String response = serviceController.get(id);

      String expected = String.format("{\"error\": \"notfound\", \"key\": \"%s\"}", id);
      assertEquals(response, expected);
    } catch (Exception e) {
      fail(e);
    }
  }
}
