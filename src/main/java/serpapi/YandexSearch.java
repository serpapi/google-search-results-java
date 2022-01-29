package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Yandex Search Results using SerpApi
 *
 * Usage 
 * --- 
 * <pre>
 * {@code 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("text", "Coffee");
 * YandexSearch yandex = new YandexSearch(parameter, "secret api key"); 
 * JsonObject data = yandex.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class YandexSearch extends SerpApiSearch {

 /**
  * Constructor
  * @param parameter search parameter
  * @param apiKey secret API key
  */
  public YandexSearch(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "yandex");
  }

 /**
  * Constructor
  */
  public YandexSearch() {
    super("yandex");
  }

 /**
  * Constructor
  * @param parameter search parameter
  */
  public YandexSearch(Map<String, String> parameter) {
    super(parameter, "yandex");
  }

  @Override
  public JsonArray getLocation(String q, Integer limit) throws SerpApiSearchException {
    throw new SerpApiSearchException("location is not supported for Baidu");
  }
}