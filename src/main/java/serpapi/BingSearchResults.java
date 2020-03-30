package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Bing Search Results using SerpApi
 *
 * Usage 
 * --- 
 * ```java 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * BingSearchResults bing = new BingSearchResults(parameter, "secret api key"); 
 * JsonObject data = bing.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
class BingSearchResults extends SerpApiClient {

  public BingSearchResults(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "bing");
  }

  public BingSearchResults() {
    super("bing");
  }

  public BingSearchResults(Map<String, String> parameter) {
    super(parameter, "bing");
  }

// end
}