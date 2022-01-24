package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Ebay Search Results using SerpApi
 *
 * Usage 
 * --- 
 * <pre>
 * {@code 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("_nkw", "Coffee");
 * EbaySearch ebay = new EbaySearch(parameter, "secret api key"); 
 * JsonObject data = ebay.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class EbaySearch extends SerpApiSearch {

 /**
  * Constructor
  * @param parameter search parameter
  * @param apiKey secret API key
  */
  public EbaySearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "ebay");
  }

  /**
   * Constructor
   */
  public EbaySearch() {
    super("ebay");
  }

  /**
  * Constructor
  * @param parameter search parameter
  */
  public EbaySearch(Map<String, String> parameter) {
    super(parameter, "ebay");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
     throw new SerpApiSearchException("location is not supported for Baidu");
   }
}