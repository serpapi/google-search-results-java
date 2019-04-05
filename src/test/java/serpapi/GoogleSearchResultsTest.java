package serpapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test main class
 */
public class GoogleSearchResultsTest {

  GoogleSearchResults client;

  @Before
  public void setUp() throws Exception {
    if (System.getenv("API_KEY") != null) {
      GoogleSearchResults.serp_api_key_default = System.getenv("API_KEY");
    }
  }

  @Test
  public void buildParameter() throws GoogleSearchException {
    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");
    client = new GoogleSearchResults(parameter);
    client.buildQuery("/search", "html");
    assertEquals(client.parameter.get("source"), "java");
    assertEquals(client.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), GoogleSearchResults.serp_api_key_default);
  }

  @Test
  public void builParameterForInstance() throws GoogleSearchException {
    GoogleSearchResults client = new GoogleSearchResults();
    client.buildQuery("/search", "json");
    assertEquals(client.parameter.get("source"), "java");
    assertEquals(client.parameter.get("output"), "json");
    assertEquals(client.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), GoogleSearchResults.serp_api_key_default);
  }

  @Test
  public void getSerpApiKey() throws Exception {
    GoogleSearchResults.serp_api_key_default = "abc";
    assertEquals("abc", GoogleSearchResults.getSerpApiKey());
  }

  @Test
  public void asJson() throws Exception {
    GoogleSearchResults client = new GoogleSearchResults();
    JsonObject expectation = new JsonObject();
    expectation.add("status", new JsonPrimitive("ok"));
    assertEquals(expectation, client.asJson("{\"status\": \"ok\"}"));
  }

  @Test
  public void getHtml() throws Exception {
    GoogleSearchResultsClient client = mock(GoogleSearchResultsClient.class);

    String htmlContent = ReadJsonFile.readAsString(Paths.get("src/test/java/serpapi/data/search_coffee_sample.html"));
    when(client.getResults(ArgumentMatchers.<String, String>anyMap())).thenReturn(htmlContent);

    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");
    GoogleSearchResults result = new GoogleSearchResults(parameter);
    result.client = client;

    assertEquals(htmlContent, result.getHtml());
  }

  @Test
  public void getJson() throws Exception {
    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");

    GoogleSearchResultsClient stub = mock(GoogleSearchResultsClient.class);
    when(stub.getResults(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/data/search_coffee_sample.json")).toString());

    GoogleSearchResults client = new GoogleSearchResults(parameter);
    client.client = stub;

    assertEquals(3, client.getJson().getAsJsonArray("local_results").size());
  }

  @Test
  public void searchCoffee() throws GoogleSearchException {
    // skip test if no api_key provided
    if(System.getenv("API_KEY") == null)
      return;

    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas, United States");
    parameter.put("hl", "en");
    parameter.put("gl", "us");
    parameter.put("google_domain", "google.com");
    // parameter.put("api_key", "demo");
    parameter.put("safe", "active");
    parameter.put("start", "10");
    parameter.put("num", "10");
    parameter.put("device", "desktop");

    GoogleSearchResults result = new GoogleSearchResults(parameter);
    JsonObject results = result.getJson();
    assertEquals(9, results.getAsJsonArray("organic_results").size());
  }

}
