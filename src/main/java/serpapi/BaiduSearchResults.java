package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Google Search Results using SerpApi
 *
 * Usage --- ```java Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee"); parameter.put("location", "Austin,Texas");
 * parameter.put(SerpApiClient.SERP_API_KEY_NAME, "your secret key");
 * SerpApiClient serp = new SerpApiClient(parameter); ```
 * 
 * JsonObject data = serp.getJson();
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