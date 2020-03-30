package serpapi;

import java.util.Map;

/***
 * Google Search Results using SerpApi
 *
 * Usage 
 * --- 
 * ```java 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * GoogleSearchResults google = new GoogleSearchResults(parameter, "secret api key"); 
 * JsonObject data = google.getJson();
 * JsonObject organic_results = data.getJsonAsObject("organic_results");
 * ```
 */
class GoogleSearchResults extends SerpApiClient {

  public GoogleSearchResults(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "google");
  }

  public GoogleSearchResults() {
    super("google");
  }

  public GoogleSearchResults(Map<String, String> parameter) {
    super(parameter, "google");
  }

// end
}