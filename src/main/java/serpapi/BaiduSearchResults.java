package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Baidu Search Results using SerpApi
 *
 * Usage 
 * --- 
 * ```java 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * BaiduSearchResults baidu = new BaiduSearchResults(parameter, "secret api key"); 
 * JsonObject data = baidu.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
class BaiduSearchResults extends SerpApiClient {

  public BaiduSearchResults(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "baidu");
  }

  public BaiduSearchResults() {
    super("baidu");
  }

  public BaiduSearchResults(Map<String, String> parameter) {
    super(parameter, "baidu");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiClientException {
     throw new SerpApiClientException("location is not supported for Baidu");
   }
}