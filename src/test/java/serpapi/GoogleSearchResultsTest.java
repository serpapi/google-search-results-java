package serpapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import serpapi.GoogleSearchException;
import serpapi.GoogleSearchResults;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Test main class
 */
public class GoogleSearchResultsTest
{
  private static final String SERP_API_INSTANCE_FAKE_KEY = "instance_key";
  private static final String SERP_API_DEFAULT_FAKE_KEY = "default_key";
  GoogleSearchResults serp;

  @Before
  public void setUp() throws Exception
  {
    GoogleSearchResults.serp_api_key_default = SERP_API_DEFAULT_FAKE_KEY;

    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Portland");
    serp = new GoogleSearchResults(parameter, SERP_API_INSTANCE_FAKE_KEY);
  }

  @Test
  public void build_connection() throws GoogleSearchException
  {
    HttpURLConnection connection = serp.buildConnection();
    assertEquals(connection.getURL().toString(), "https://serpapi.com/search?location=Portland&q=Coffee");
  }

  @Test
  public void buildParameter() throws GoogleSearchException
  {
    serp.buildParameter();
    assertEquals(serp.parameter.get("source"), "java");
    assertEquals(serp.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), SERP_API_INSTANCE_FAKE_KEY);
  }
  @Test
  public void builParameterForInstance() throws GoogleSearchException
  {
    GoogleSearchResults serp = new GoogleSearchResults(new HashMap<String, String>());
    serp.buildParameter();
    assertEquals(serp.parameter.get("source"), "java");
    assertEquals(serp.parameter.get(GoogleSearchResults.SERP_API_KEY_NAME), SERP_API_DEFAULT_FAKE_KEY);
  }

  @Test
  public void getSerpApiKey() throws Exception
  {
    GoogleSearchResults.serp_api_key_default = "abc";
    assertEquals("abc", serp.getSerpApiKey());
  }

  @Test
  public void get_results() throws Exception
  {

  }

  @Test
  public void getHtml() throws Exception
  {
    GoogleSearchResults gsr = mock(GoogleSearchResults.class);
    when(gsr.getResults()).thenReturn("ok");

    assertEquals(gsr.getJson().toString(), "");
  }

  @Test
  public void asJson() throws Exception
  {
    GoogleSearchResults gsr = new GoogleSearchResults(new HashMap<String, String>());
    JsonObject expectation = new JsonObject();
    expectation.add("status", new JsonPrimitive("ok"));
    assertEquals(expectation, gsr.asJson("{\"status\": \"ok\"}"));
  }

  @Mock
  private GoogleSearchResults gsr;

  @Test
  public void getJson() throws Exception
  {
    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Portland");

    when(gsr.getResults()).thenReturn("{\"status\": \"ok\"}");
    assertEquals("", gsr.getJson().toString());
  }

  @Test
  public void getJsonWithImages() throws Exception
  {
    GoogleSearchResults gsr = mock(GoogleSearchResults.class);
    when(gsr.getResults()).thenReturn("ok");

    assertEquals(gsr.getJson().toString(), "");
  }

}