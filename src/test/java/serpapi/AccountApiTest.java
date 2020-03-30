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
      GoogleSearchResults.serp_api_key_default = System.getenv("API_KEY");
    }
  }

  @Test
  public void getAccount() throws Exception {
    String expected_api_key = GoogleSearchResults.serp_api_key_default;

    // mock response if run on github
    GoogleSearchResults client = new GoogleSearchResults();
    if (System.getenv("API_KEY") == null) {
      SerpApiHttpClient stub = mock(SerpApiHttpClient.class);
      String data = ReadJsonFile.readAsString(Paths.get("src/test/java/serpapi/data/account.json"));
      when(stub.getResults(ArgumentMatchers.<String, String>anyMap())).thenReturn(data);
      client.client = stub;

      // fallback to default
      expected_api_key = "demo";
    }

    JsonObject info = client.getAccount();
    System.out.println(info.toString());
    assertEquals(expected_api_key, info.getAsJsonObject().get("api_key").getAsString());
  }
}