package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Google Search Results using SerpApi
 *
 * Usage 
 * --- 
 * <pre>
 * {@code 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * GoogleSearch google = new GoogleSearch(parameter, "secret api key"); 
 * JsonObject data = google.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class GoogleSearch extends SerpApiSearch {

 /**
  * Constructor
  * @param parameter search parameter
  * @param apiKey secret API key
  */
  public GoogleSearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "google");
  }

 /**
  * Constructor
  */
  public GoogleSearch() {
    super("google");
  }

 /**
  * Constructor
  * @param parameter search parameter
  */
  public GoogleSearch(Map<String, String> parameter) {
    super(parameter, "google");
  }

// end
}