package com.serpapi.query.account;

import com.google.gson.JsonObject;
import com.serpapi.SerpApiUnitTest;
import com.serpapi.client.SerpApiSyncClient;
import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.AccountResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AccountTest extends SerpApiUnitTest {

  private SerpApiSyncClient client;

  @Before
  public void testSetup() throws SerpApiException {
     client = new SerpApiSyncClient();
  }

  @After
  public void testTeardown() throws IOException {
    if (client != null) {
      client.close();
    }
  }

  @Test
  public void accountTest() throws SerpApiException {
    AccountResponse accountResponse = client.account(getTestApiKey());
    JsonObject account = accountResponse.getJsonObject();
    Assert.assertEquals(getTestApiKey(), new ApiKey(account.get("api_key").getAsString()));
  }
}
