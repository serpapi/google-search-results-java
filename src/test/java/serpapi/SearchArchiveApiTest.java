package serpapi;

import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;
import static org.junit.Assert.*;

public class SearchArchiveApiTest {

  @Test
  public void getSearchArchive() throws GoogleSearchException {
    // skip test if no api_key provided
    if(System.getenv("API_KEY") == null)
      return;

    GoogleSearchResults.serp_api_key_default = System.getenv("API_KEY");

    Map<String, String> parameter = new HashMap<>();
    parameter.put("q", "Coffee");
    parameter.put("location", "Austin,Texas");

    GoogleSearchResults client = new GoogleSearchResults(parameter);
    JsonObject result = client.getJson();

    int search_id = result.get("search_metadata").getAsJsonObject().get("id").getAsInt();
    JsonObject archived_result = client.getSearchArchive(search_id);
    System.out.println(archived_result.toString());

    assertEquals(search_id, archived_result.get("search_metadata").getAsJsonObject().get("id").getAsInt());
  }
}