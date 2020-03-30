package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Yahoo Search Results using SerpApi
 *
 * Usage 
 * --- 
 * ```java 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("p", "Coffee");
 * YahooSearchResults yahoo = new YahooSearchResults(parameter, "secret api key"); 
 * JsonObject data = yahoo.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
class YahooSearchResults extends SerpApiClient {

  public YahooSearchResults(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "yahoo");
  }

  public YahooSearchResults() {
    super("yahoo");
  }

  public YahooSearchResults(Map<String, String> parameter) {
    super(parameter, "yahoo");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiClientException {
     throw new SerpApiClientException("location is not supported for Baidu");
   }
}