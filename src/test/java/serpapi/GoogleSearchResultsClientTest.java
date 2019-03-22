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
public class GoogleSearchResultsClientTest
{
  GoogleSearchResultsClient client;
  private HashMap<String, String> parameter;

  @Before
  public void setUp() throws Exception
  {
    client = new GoogleSearchResultsClient("/search");

    parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin, Texas");
    parameter.put("output", "json");

    String api_key = System.getenv("API_KEY");
    if(api_key== null) {
        api_key = "demo";
    }
    parameter.put(GoogleSearchResults.SERP_API_KEY_NAME, api_key);
  }

  @Test
  public void buildConnection() throws GoogleSearchException
  {
    HttpURLConnection connection = client.buildConnection("/search", parameter);
    assertEquals("https://serpapi.com/search?output=json&q=Coffee&serp_api_key=" + GoogleSearchResults.serp_api_key_default + "&location=Austin%2C+Texas", connection.getURL().toString());
  }

  @Test
  public void triggerGoogleSearchException() throws Exception
  {
    try {
      String content = ReadJsonFile.readAsJson(Paths.get("src/test/java/serpapi/error_sample.json")).toString();
      client.triggerGoogleSearchException(content);
    }
    catch(Exception ex)
    {
      assertEquals(GoogleSearchException.class, ex.getClass());
    }
  }

}
