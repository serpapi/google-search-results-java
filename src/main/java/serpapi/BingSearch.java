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
 * BingSearch bing = new BingSearch(parameter, "secret api key"); 
 * JsonObject data = bing.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
public class BingSearch extends SerpApiSearch {

  public BingSearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "bing");
  }

  public BingSearch() {
    super("bing");
  }

  public BingSearch(Map<String, String> parameter) {
    super(parameter, "bing");
  }

// end
}