package serpapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test ebay search results 
 */
public class EbaySearchTest {

  EbaySearch search;

  @Before
  public void setUp() throws Exception {
    if (System.getenv("API_KEY") != null) {
      EbaySearch.api_key_default = System.getenv("API_KEY");
    }
  }

  @Test
  public void searchCoffee() throws SerpApiSearchException {
    // skip test if no api_key provided
    if (System.getenv("API_KEY") == null)
      return;

    Map<String, String> parameter = new HashMap<>();
    parameter.put("_nkw", "Coffee");

    EbaySearch result = new EbaySearch(parameter);
    JsonObject results = result.getJson();
    System.out.println(results);
    assertTrue(results.getAsJsonArray("organic_results").size() > 5);
  }

}
