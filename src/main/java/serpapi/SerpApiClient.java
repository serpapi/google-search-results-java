package serpapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.HashMap;

/**
 * SerpApiClient wraps HTTP interaction with the service serpapi.com
 */
public class SerpApiClient extends Exception {
  // Set of constant
  public static final String SERP_API_KEY_NAME = "api_key";

  // default static key
  public static String serp_api_key_default;

  // instance level key
  private String api_key;

  // current search engine
  private String engine;

  // persist query parameter
  public Map<String, String> parameter;

  // initialize gson
  private static Gson gson = new Gson();

  // https client implementation for Java 7+
  public BasicHttpClient client;

  /*
   * Constructor
   *
   * @param parameter
   * 
   * @param serp_api_key
   */
  public SerpApiClient(Map<String, String> parameter, String api_key, String engine) {
    this.parameter = parameter;
    this.api_key = api_key;
    this.engine = engine;
  }

  /***
   * Constructor
   *
   * @param parameter
   */
  public SerpApiClient(Map<String, String> parameter, String engine) {
    this.parameter = parameter;
    this.engine = engine;
  }

  /***
   * Constructor with no parameter
   */
  public SerpApiClient(String engine) {
    this.parameter = new HashMap<String, String>();
    this.engine = engine;
  }

  /*
   * Constructor
   *
   * @param serp_api_key
   */
  public SerpApiClient(String serp_api_key, String engine) {
    this.api_key = serp_api_key;
    this.engine = engine;
  }

  /***
   * Build a serp API query by expanding existing parameter
   *
   * @param output type of output format (json, html, json_with_images)
   * @return query
   * @throws SerpApiClientException
   */
  public Map<String, String> buildQuery(String path, String output) throws SerpApiClientException {
    // Initialize client if not done
    if (client == null) {
      this.client = new BasicHttpClient(path);
      this.client.setHttpConnectionTimeout(6000);
    } else {
      this.client.path = path;
    }

    // Set current programming language
    this.parameter.put("source", "java");

    // Set serp_api_key
    if (this.parameter.get(SERP_API_KEY_NAME) == null) {
      if (this.api_key != null) {
        this.parameter.put(SERP_API_KEY_NAME, this.api_key);
      } else if (getSerpApiKey() != null) {
        this.parameter.put(SERP_API_KEY_NAME, getSerpApiKey());
      } else {
        // throw new SerpApiClientException(SERP_API_KEY_NAME + " is not defined");
      }
    }

    this.parameter.put("engine", this.engine);

    // Set output format
    this.parameter.put("output", output);

    return this.parameter;
  }

  public static String getSerpApiKey() {
    return serp_api_key_default;
  }

  /***
   * Get HTML output
   * 
   * @return String
   * @throws SerpApiClientException
   */
  public String getHtml() throws SerpApiClientException {
    Map<String, String> query = buildQuery("/search", "html");
    return client.getResults(query);
  }

  /***
   * Get JSON output
   * 
   * @return JsonObject parent node
   * @throws SerpApiClientException
   */
  public JsonObject getJson() throws SerpApiClientException {
    Map<String, String> query = buildQuery("/search", "json");
    return asJson(client.getResults(query));
  }

  /***
   * Convert HTTP content to JsonValue
   * 
   * @param content
   * @return JsonObject
   */
  public JsonObject asJson(String content) {
    JsonElement element = gson.fromJson(content, JsonElement.class);
    return element.getAsJsonObject();
  }

  /***
   * @return http client
   */
  public BasicHttpClient getClient() {
    return this.client;
  }

  /***
   * Get location
   * 
   * @param q     query
   * @param limit number of location
   * @return JsonObject location using Location API
   * @throws SerpApiClientException
   */
  public JsonArray getLocation(String q, Integer limit) throws SerpApiClientException {
    Map<String, String> query = buildQuery("/locations.json", "json");
    query.remove("output");
    query.remove(SERP_API_KEY_NAME);
    query.put("q", q);
    query.put("limit", limit.toString());
    String s = client.getResults(query);
    return gson.fromJson(s, JsonArray.class);
  }

  /***
   * Get search result from the Search Archive API
   * 
   * @param searchID archived search result = search_metadata.id
   * @return JsonObject search result
   * @throws SerpApiClientException
   */
  public JsonObject getSearchArchive(String searchID) throws SerpApiClientException {
    Map<String, String> query = buildQuery("/searches/" + searchID + ".json", "json");
    query.remove("output");
    query.remove("q");
    return asJson(client.getResults(query));
  }

  /***
   * Get account information using Account API
   * 
   * @return JsonObject account information
   * @throws SerpApiClientException
   */
  public JsonObject getAccount() throws SerpApiClientException {
    Map<String, String> query = buildQuery("/account", "json");
    query.remove("output");
    query.remove("q");
    return asJson(client.getResults(query));
  }

}
