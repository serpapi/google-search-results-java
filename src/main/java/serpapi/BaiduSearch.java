package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Baidu Search Results using SerpApi
 *
 * Usage 
 * --- 
 * <pre>
 * {@code 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * BaiduSearch baidu = new BaiduSearch(parameter, "secret api key"); 
 * JsonObject data = baidu.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class BaiduSearch extends SerpApiSearch {

 /**
  * Constructor
  * @param parameter search parameter
  * @param apiKey secret API key
  */
  public BaiduSearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "baidu");
  }

  /**
   * Constructor
   */
  public BaiduSearch() {
    super("baidu");
  }

  /**
  * Constructor
  * @param parameter search parameter
  */
  public BaiduSearch(Map<String, String> parameter) {
    super(parameter, "baidu");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
     throw new SerpApiSearchException("location is not supported for Baidu");
   }
}