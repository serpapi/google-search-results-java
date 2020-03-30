package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Ebay Search Results using SerpApi
 *
 * Usage 
 * --- 
 * ```java 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("_nkw", "Coffee");
 * EbaySearchResults ebay = new EbaySearchResults(parameter, "secret api key"); 
 * JsonObject data = ebay.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
class EbaySearchResults extends SerpApiClient {

  public EbaySearchResults(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "ebay");
  }

  public EbaySearchResults() {
    super("ebay");
  }

  public EbaySearchResults(Map<String, String> parameter) {
    super(parameter, "ebay");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiClientException {
     throw new SerpApiClientException("location is not supported for Baidu");
   }
}