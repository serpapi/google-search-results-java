package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Yahoo Search Results using SerpApi
 *
 * Usage 
 * --- 
 * <pre>
 * {@code 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("p", "Coffee");
 * YahooSearch yahoo = new YahooSearch(parameter, "secret api key"); 
 * JsonObject data = yahoo.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class YahooSearch extends SerpApiSearch {

  public YahooSearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "yahoo");
  }

  public YahooSearch() {
    super("yahoo");
  }

  public YahooSearch(Map<String, String> parameter) {
    super(parameter, "yahoo");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
     throw new SerpApiSearchException("location is not supported for Baidu");
   }
}