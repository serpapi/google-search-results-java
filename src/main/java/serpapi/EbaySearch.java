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
 * EbaySearch ebay = new EbaySearch(parameter, "secret api key"); 
 * JsonObject data = ebay.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
public class EbaySearch extends SerpApiSearch {

  public EbaySearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "ebay");
  }

  public EbaySearch() {
    super("ebay");
  }

  public EbaySearch(Map<String, String> parameter) {
    super(parameter, "ebay");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
     throw new SerpApiSearchException("location is not supported for Baidu");
   }
}