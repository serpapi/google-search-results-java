package serpapi;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountApiTest {

  @Before
  public void setUp() throws Exception {
    if (System.getenv("API_KEY") != null) {
      GoogleSearch.api_key_default = System.getenv("API_KEY");
    }
  }

  @Test
  public void getAccount() throws Exception {
    String expected_api_key = GoogleSearch.api_key_default;

    // mock response if run on github
    GoogleSearch search = new GoogleSearch();
    if (System.getenv("API_KEY") == null) {
      SerpApiHttpClient stub = mock(SerpApiHttpClient.class);
      String data = ReadJsonFile.readAsString(Paths.get("src/test/java/serpapi/data/account.json"));
      when(stub.getResults(ArgumentMatchers.<String, String>anyMap())).thenReturn(data);
      search.search = stub;

      // fallback to default
      expected_api_key = "demo";
    }

    JsonObject info = search.getAccount();
    System.out.println(info.toString());
    assertEquals(expected_api_key, info.getAsJsonObject().get("api_key").getAsString());
  }
}