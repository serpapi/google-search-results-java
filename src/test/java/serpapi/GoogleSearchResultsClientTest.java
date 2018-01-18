package serpapi;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

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
    client = new GoogleSearchResultsClient();

    parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Portland");
    parameter.put("output", "json");
    parameter.put(GoogleSearchResults.SERP_API_KEY_NAME, "demo");
  }

  @Test
  public void build_connection() throws GoogleSearchException
  {
    HttpURLConnection connection = client.buildConnection(parameter);
    assertEquals("https://serpapi.com/search?output=json&q=Coffee&serp_api_key=demo&location=Portland", connection.getURL().toString());
  }

  @Test
  public void getResults() throws Exception
  {

  }

}