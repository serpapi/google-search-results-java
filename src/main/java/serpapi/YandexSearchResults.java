package serpapi;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Yandex Search Results using SerpApi
 *
 * Usage 
 * --- 
 * ```java 
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("text", "Coffee");
 * YandexSearchResults yandex = new YandexSearchResults(parameter, "secret api key"); 
 * JsonObject data = yandex.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * ```
 */
class YandexSearchResults extends SerpApiClient {

  public YandexSearchResults(Map<String, String> parameter, String apiKey) {
    super(parameter, apiKey, "yandex");
  }

  public YandexSearchResults() {
    super("yandex");
  }

  public YandexSearchResults(Map<String, String> parameter) {
    super(parameter, "yandex");
  }

  @Override
   public JsonArray getLocation(String q, Integer limit) throws SerpApiClientException {
     throw new SerpApiClientException("location is not supported for Baidu");
   }
}