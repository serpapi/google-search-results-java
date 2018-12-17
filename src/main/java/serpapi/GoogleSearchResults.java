package serpapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

/***
 * Google Search Results using SerpApi
 *
 * Usage
 * ---
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * parameter.put("location", "Austin,Texas");
 * parameter.put(GoogleSearchResults.SERP_API_KEY_NAME, "demo");
 * GoogleSearchResults serp = new GoogleSearchResults(parameter);
 * 
 * JsonObject data = serp.getJson();
 */
public class GoogleSearchResults extends Exception
{
  // Set of constant
  public static final String SERP_API_KEY_NAME = "serp_api_key";

  // default static key
  public static String serp_api_key_default;

  // instance level key
  private String serp_api_key;

  // persist query parameter
  public Map<String,String> parameter;

  // initialize gson
  private static Gson gson = new Gson();

  // https client implementation for Java 7+
  public GoogleSearchResultsClient client;

  /*
   * Constructor
   *
   * @param parameter
   * @param serp_api_key
   */
  public GoogleSearchResults(Map<String,String> parameter, String serp_api_key)
  {
    this.parameter = parameter;
    this.serp_api_key = serp_api_key;
  }

  /***
   * Constructor
   *
   * @param parameter
   */
  public GoogleSearchResults(Map<String,String> parameter)
  {
    this.parameter = parameter;
  }

  /***
   * Build a serp API query by expanding existing parameter
   *
   * @param output type of output format (json, html, json_with_images)
   * @return query
   * @throws GoogleSearchException
   */
  public Map<String, String> buildQuery(String output) throws GoogleSearchException
  {
    // Initialize client if not done
    if(client == null)
    {
      this.client = new GoogleSearchResultsClient();
      this.client.setHttpConnectionTimeout(6000);
    }

    // Set current programming language
    this.parameter.put("source", "java");

    // Set serp_api_key
    if(this.parameter.get(SERP_API_KEY_NAME) == null)
    {
      if(this.serp_api_key != null)
      {
        this.parameter.put(SERP_API_KEY_NAME, this.serp_api_key);
      }
      else if(getSerpApiKey() != null)
      {
        this.parameter.put(SERP_API_KEY_NAME, getSerpApiKey());
      }
      else
      {
        throw new GoogleSearchException(SERP_API_KEY_NAME + " is not defined");
      }
    }

    // Set output format
    this.parameter.put("output", output);

    return this.parameter;
  }

  public static String getSerpApiKey()
  {
    return serp_api_key_default;
  }

  /***
   * Get HTML output
   * @return String
   * @throws GoogleSearchException
   */
  public String getHtml() throws GoogleSearchException
  {
    Map<String, String> query = buildQuery("html");
    return client.getResults(query);
  }

  /***
   * Get JSON output
   * @return JsonObject parent node
   * @throws GoogleSearchException
   */
  public JsonObject getJson() throws GoogleSearchException
  {
    Map<String, String> query = buildQuery("json");
    return asJson(client.getResults(query));
  }

  /***
   * Convert HTTP content to JsonValue
   * @param content
   * @return JsonObject
   */
  public JsonObject asJson(String content)
  {
    JsonElement element = gson.fromJson(content, JsonElement.class);
    return element.getAsJsonObject();
  }

  public GoogleSearchResultsClient getClient()
  {
    return this.client;
  }

}
