package serpapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.HashMap;

/**
 * SerpApiSearch wraps HTTP interaction with the service serpapi.com
 */
public class SerpApiSearch extends Exception {
  /** 
   * Set of constant
   */
  public static final String API_KEY_NAME = "api_key";

  /**
   * default static key
   */ 
  public static String api_key_default;

  /**
   * user secret API key
   */
  protected String api_key;

  /**
  * Current search engine
  */
  protected String engine;

 /** 
  * search parameters
  */
  public Map<String, String> parameter;

  // initialize gson
  private static Gson gson = new Gson();

  /**
  * https search implementation for Java 7+
  */
  public SerpApiHttpClient search;

  /***
   * Constructor
   *
   * @param parameter user search
   * @param api_key secret user API key
   * @param engine service like: google, naver, yahoo...
   */
  public SerpApiSearch(Map<String, String> parameter, String api_key, String engine) {
    this.parameter = parameter;
    this.api_key = api_key;
    this.engine = engine;
  }

  /***
   * Constructor
   *
   * @param parameter user search
   * @param engine service like: google, yahoo, bing...
   */
  public SerpApiSearch(Map<String, String> parameter, String engine) {
    this.parameter = parameter;
    this.engine = engine;
  }

 /***
  * Constructor with no parameter
  * @param engine service like: google, bing, yahoo...
  */
  public SerpApiSearch(String engine) {
    this.parameter = new HashMap<String, String>();
    this.engine = engine;
  }

 /** 
  * Constructor
  *
  * @param api_key secret API key
  * @param engine service like: google, bing, yahoo...
  */
  public SerpApiSearch(String api_key, String engine) {
    this.api_key = api_key;
    this.engine = engine;
  }

  /***
   * Build a serp API query by expanding existing parameter
   *
   * @param path backend HTTP path
   * @param output type of output format (json, html, json_with_images)
   * @return format parameter hash map
   * @throws SerpApiSearchException wraps backend error message
   */
  public Map<String, String> buildQuery(String path, String output) throws SerpApiSearchException {
    // Initialize search if not done
    if (search == null) {
      this.search = new SerpApiHttpClient(path);
      this.search.setHttpConnectionTimeout(6000);
    } else {
      this.search.path = path;
    }

    // Set current programming language
    this.parameter.put("source", "java");

    // Set api_key
    if (this.parameter.get(API_KEY_NAME) == null) {
      if (this.api_key != null) {
        this.parameter.put(API_KEY_NAME, this.api_key);
      } else if (getApiKey() != null) {
        this.parameter.put(API_KEY_NAME, getApiKey());
      } else {
        throw new SerpApiSearchException(API_KEY_NAME + " is not defined");
      }
    }

    this.parameter.put("engine", this.engine);

    // Set output format
    this.parameter.put("output", output);

    return this.parameter;
  }

 /**
  * @return current secret api key
  */
  public static String getApiKey() {
    return api_key_default;
  }

  /***
   * Get HTML output
   * 
   * @return raw HTML response from the search engine for custom parsing
   * @throws SerpApiSearchException wraps backend error message
   */
  public String getHtml() throws SerpApiSearchException {
    Map<String, String> query = buildQuery("/search", "html");
    return search.getResults(query);
  }

  /***
   * Get JSON output
   * 
   * @return JsonObject parent node
   * @throws SerpApiSearchException wraps backend error message
   */
  public JsonObject getJson() throws SerpApiSearchException {
    Map<String, String> query = buildQuery("/search", "json");
    return asJson(search.getResults(query));
  }

  /***
   * Convert HTTP content to JsonValue
   * 
   * @param content raw JSON HTTP response
   * @return JsonObject created by gson parser
   */
  public JsonObject asJson(String content) {
    JsonElement element = gson.fromJson(content, JsonElement.class);
    return element.getAsJsonObject();
  }

  /***
   * @return http search
   */
  public SerpApiHttpClient getClient() {
    return this.search;
  }

  /***
   * Get location
   * 
   * @param q     query
   * @param limit number of location
   * @return JsonObject location using Location API
   * @throws SerpApiSearchException wraps backend error message
   */
  public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
    Map<String, String> query = buildQuery("/locations.json", "json");
    query.remove("output");
    query.remove(API_KEY_NAME);
    query.put("q", q);
    query.put("limit", limit.toString());
    String s = search.getResults(query);
    return gson.fromJson(s, JsonArray.class);
  }

  /***
   * Get search result from the Search Archive API
   * 
   * @param searchID archived search result = search_metadata.id
   * @return JsonObject search result
   * @throws SerpApiSearchException wraps backend error message
   */
  public JsonObject getSearchArchive(String searchID) throws SerpApiSearchException {
    Map<String, String> query = buildQuery("/searches/" + searchID + ".json", "json");
    query.remove("output");
    query.remove("q");
    return asJson(search.getResults(query));
  }

  /***
   * Get account information using Account API
   * 
   * @return JsonObject account information
   * @throws SerpApiSearchException wraps backend error message
   */
  public JsonObject getAccount() throws SerpApiSearchException {
    Map<String, String> query = buildQuery("/account", "json");
    query.remove("output");
    query.remove("q");
    return asJson(search.getResults(query));
  }

}
