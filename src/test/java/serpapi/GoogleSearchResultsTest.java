package serpapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test main class
 */
public class GoogleSearchResultsTest
{
  private static final String SERP_API_INSTANCE_FAKE_KEY = "demo";
  private static final String SERP_API_DEFAULT_FAKE_KEY = "demo";

  GoogleSearchResults search;

  @Before
  public void setUp() throws Exception
  {
    GoogleSearchResults.serp_api_key_default = SERP_API_DEFAULT_FAKE_KEY;

    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Portland");
    search = new GoogleSearchResults(parameter, SERP_API_INSTANCE_FAKE_KEY);
  }

  @Test
  public void realWorldExample() throws GoogleSearchException
  {
    Map<String, String> parameter = new HashMap<>();

    parameter.put("q", "Coffee");
    parameter.put("location", "Portland, Oregon, United States");
    parameter.put("hl", "en");
    parameter.put("gl", "us");
    parameter.put("google_domain", "google.com");
    parameter.put("api_key", "demo");
    parameter.put("safe", "active");
    parameter.put("start", "10");
    parameter.put("num", "10");
    parameter.put("device", "desktop");

    GoogleSearchResults serp = new GoogleSearchResults(parameter);
    JsonObject results = serp.getJson();

    assertEquals(9, results.getAsJsonArray("organic_results").size());
  }

  @Test
  public void buildParameter() throws GoogleSearchException
  {
    search.buildQuery("html");
    assertEquals(search.parameter.get("source"), "java");
    assertEquals(search.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), SERP_API_INSTANCE_FAKE_KEY);
  }
  @Test
  public void builParameterForInstance() throws GoogleSearchException
  {
    GoogleSearchResults serp = new GoogleSearchResults(new HashMap<String, String>());
    serp.buildQuery("json");
    assertEquals(serp.parameter.get("source"), "java");
    assertEquals(serp.parameter.get("output"), "json");
    assertEquals(serp.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), SERP_API_DEFAULT_FAKE_KEY);
  }

  @Test
  public void getSerpApiKey() throws Exception
  {
    GoogleSearchResults.serp_api_key_default = "abc";
    assertEquals("abc", search.getSerpApiKey());
  }

  @Test
  public void asJson() throws Exception
  {
    GoogleSearchResults gsr = new GoogleSearchResults(new HashMap<String, String>());
    JsonObject expectation = new JsonObject();
    expectation.add("status", new JsonPrimitive("ok"));
    assertEquals(expectation, gsr.asJson("{\"status\": \"ok\"}"));
  }

  @Test
  public void getHtml() throws Exception
  {
    GoogleSearchResultsClient client = mock(GoogleSearchResultsClient.class);

    String htmlContent = ReadJsonFile.readAsString(Paths.get("src/test/java/serpapi/search_coffee_sample.html"));
    when(client.getResults(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(htmlContent);
    GoogleSearchResults gsr = new GoogleSearchResults(search.parameter);
    gsr.client = client;

    assertEquals(htmlContent, gsr.getHtml());
  }

  @Test
  public void getJson() throws Exception
  {
    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Portland");

    GoogleSearchResultsClient client = mock(GoogleSearchResultsClient.class);

    when(client.getResults(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/search_coffee_sample.json")).toString());

    GoogleSearchResults gsr = new GoogleSearchResults(search.parameter);
    gsr.client = client;

    assertEquals(3, gsr.getJson().getAsJsonArray("local_results").size());
  }

}
