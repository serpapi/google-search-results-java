package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

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
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
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