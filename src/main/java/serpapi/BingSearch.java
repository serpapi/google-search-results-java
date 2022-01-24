package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Bing Search Results using SerpApi
 *
 * Usage 
 * --- 
 * <pre>
 * {@code 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * BingSearch bing = new BingSearch(parameter, "secret api key"); 
 * JsonObject data = bing.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class BingSearch extends SerpApiSearch {

 /**
  * Constructor
  * @param parameter search parameter
  * @param apiKey secret API key
  */
  public BingSearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "bing");
  }

  /**
   * Constructor
   */
  public BingSearch() {
    super("bing");
  }

 /**
  * Constructor
  * @param parameter search parameter
  */
  public BingSearch(Map<String, String> parameter) {
    super(parameter, "bing");
  }

// end
}