package serpapi;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;

import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Test HTTP client
 */
public class GoogleSearchResultsClientTest {
  SerpApiHttpClient client;
  private HashMap<String, String> parameter;

  @Before
  public void setUp() throws Exception {
    client = new SerpApiHttpClient("/search");

    parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");
    parameter.put("output", "json");

    String api_key = System.getenv("API_KEY");
    if (api_key == null) {
      GoogleSearchResults.serp_api_key_default = "demo";
      parameter.put(GoogleSearchResults.SERP_API_KEY_NAME, "demo");
    } else {
      parameter.put(GoogleSearchResults.SERP_API_KEY_NAME, api_key);
    }
  }

  @Test
  public void buildConnection() throws SerpApiClientException {
    HttpURLConnection connection = client.buildConnection("/search", parameter);
    assertEquals("https://serpapi.com/search?output=json&q=Coffee&api_key=" + GoogleSearchResults.serp_api_key_default
        + "&location=Austin%2C+Texas", connection.getURL().toString());
  }

  @Test
  public void triggerSerpApiClientException() throws Exception {
    try {
      String content = ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/data/error_sample.json")).toString();
      client.triggerSerpApiClientException(content);
    } catch (Exception ex) {
      assertEquals(SerpApiClientException.class, ex.getClass());
    }
  }

}
