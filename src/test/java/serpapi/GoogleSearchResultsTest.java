package serpapi;

import com.google.gson.JsonArray;
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
public class GoogleSearchResultsTest
{
  private static final String SERP_API_DEFAULT_FAKE_KEY = "demo";

  GoogleSearchResults search;

  @Before
  public void setUp() throws Exception
  {
    GoogleSearchResults.serp_api_key_default = System.getenv("API_KEY");
    if(GoogleSearchResults.serp_api_key_default == null) {
      GoogleSearchResults.serp_api_key_default = SERP_API_DEFAULT_FAKE_KEY;
    }

    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");
    search = new GoogleSearchResults(parameter);
  }

  @Test
  public void searchCoffee() throws GoogleSearchException
  {
    Map<String, String> parameter = new HashMap<>();

    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas, United States");
    parameter.put("hl", "en");
    parameter.put("gl", "us");
    parameter.put("google_domain", "google.com");
    //parameter.put("api_key", "demo");
    parameter.put("safe", "active");
    parameter.put("start", "10");
    parameter.put("num", "10");
    parameter.put("device", "desktop");

    GoogleSearchResults result = new GoogleSearchResults(parameter);
    JsonObject results = result.getJson();
    assertTrue(results.getAsJsonArray("organic_results").size() >= 10);
  }

  @Test
  public void buildParameter() throws GoogleSearchException
  {
    search.buildQuery("/search", "html");
    assertEquals(search.parameter.get("source"), "java");
    assertEquals(search.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), GoogleSearchResults.serp_api_key_default);
  }
  @Test
  public void builParameterForInstance() throws GoogleSearchException
  {
    GoogleSearchResults serp = new GoogleSearchResults(new HashMap<String, String>());
    serp.buildQuery("/search", "json");
    assertEquals(serp.parameter.get("source"), "java");
    assertEquals(serp.parameter.get("output"), "json");
    assertEquals(serp.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), GoogleSearchResults.serp_api_key_default);
  }

  @Test
  public void getSerpApiKey() throws Exception
  {
    GoogleSearchResults.serp_api_key_default = "abc";
    assertEquals("abc", GoogleSearchResults.getSerpApiKey());
  }

  @Test
  public void asJson() throws Exception
  {
    GoogleSearchResults client = new GoogleSearchResults(new HashMap<String, String>());
    JsonObject expectation = new JsonObject();
    expectation.add("status", new JsonPrimitive("ok"));
    assertEquals(expectation, client.asJson("{\"status\": \"ok\"}"));
  }

  @Test
  public void getHtml() throws Exception
  {
    GoogleSearchResultsClient client = mock(GoogleSearchResultsClient.class);

    String htmlContent = ReadJsonFile.readAsString(Paths.get("src/test/java/serpapi/search_coffee_sample.html"));
    when(client.getResults(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(htmlContent);

    GoogleSearchResults result = new GoogleSearchResults(search.parameter);
    result.client = client;

    assertEquals(htmlContent, result.getHtml());
  }

  @Test
  public void getJson() throws Exception
  {
    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");

    GoogleSearchResultsClient client = mock(GoogleSearchResultsClient.class);

    when(client.getResults(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/search_coffee_sample.json")).toString());

    GoogleSearchResults result = new GoogleSearchResults(search.parameter);
    result.client = client;

    assertEquals(3, result.getJson().getAsJsonArray("local_results").size());
  }

  @Test
  public void getLocation() throws Exception
  {
    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");

    // mock response if run on github
    GoogleSearchResults result = new GoogleSearchResults(search.parameter);
    if(GoogleSearchResults.serp_api_key_default == null) 
    {
      GoogleSearchResultsClient client = mock(GoogleSearchResultsClient.class);
      when(client.getResults(ArgumentMatchers.<String, String>anyMap()))
          .thenReturn(ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/location.json")).toString());
      result.client = client;
    }

    // client.client = client;
    JsonArray location = result.getLocation("Austin", 3);
    System.out.println(location.toString());
    assertEquals("Austin, TX", location.get(0).getAsJsonObject().get("name").getAsString());
  }

  @Test
  public void getSearchArchive() throws GoogleSearchException
  {

  }

}
