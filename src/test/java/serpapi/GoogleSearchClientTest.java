package serpapi;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;

import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Test HTTP search
 */
public class GoogleSearchClientTest {
  SerpApiHttpClient search;
  private HashMap<String, String> parameter;

  @Before
  public void setUp() throws Exception {
    search = new SerpApiHttpClient("/search");

    parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");
    parameter.put("output", "json");

    String api_key = System.getenv("API_KEY");
    if (api_key == null) {
      GoogleSearch.api_key_default = "demo";
      parameter.put(GoogleSearch.API_KEY_NAME, "demo");
    } else {
      parameter.put(GoogleSearch.API_KEY_NAME, api_key);
    }
  }

  @Test
  public void buildConnection() throws SerpApiSearchException {
    HttpURLConnection connection = search.buildConnection("/search", parameter);
    assertEquals("https://serpapi.com/search?output=json&q=Coffee&api_key=" + GoogleSearch.api_key_default
        + "&location=Austin%2C+Texas", connection.getURL().toString());
  }

  @Test
  public void triggerSerpApiClientException() throws Exception {
    try {
      String content = ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/data/error_sample.json")).toString();
      search.triggerSerpApiClientException(content);
    } catch (Exception ex) {
      assertEquals(SerpApiSearchException.class, ex.getClass());
    }
  }

}
