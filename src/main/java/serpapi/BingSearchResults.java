package serpapi;

import java.util.Map;

/***
 * Bing Search Results using SerpApi
 *
 * Usage --- ```java Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee"); parameter.put("location", "Austin,Texas");
 * parameter.put(SerpApiClient.SERP_API_KEY_NAME, "your secret key");
 * SerpApiClient serp = new SerpApiClient(parameter); ```
 * 
 * JsonObject data = serp.getJson();
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
}