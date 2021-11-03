package com.serpapi.query.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.responses.SearchResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/18/21
 * Time: 8:33 AM
 */
public class SearchParamsTest extends AbstractSearchTest {

    private static final List<AbstractSearchParamsBuilder> PROVIDERS = Arrays.asList(
            new BaiduSearchParamsBuilder(),
            new BingSearchParamsBuilder(),
            new DuckDuckGoSearchParamsBuilder(),
            new EbaySearchParamsBuilder(),
            new GoogleSearchParamsBuilder(),
            new YahooSearchParamsBuilder(),
            new YandexSearchParamsBuilder()
    );

    @Test
    public void searchCoffeeWithParams() throws SerpApiException, IOException {

        // Search with every provider
        for (AbstractSearchParamsBuilder providerParams : PROVIDERS) {
            providerParams.withSearchItem("Coffee");
            SearchResponse searchResponse = searchClient.search(getTestApiKey(), providerParams);
            JsonObject results = searchResponse.getJsonObject();

            FileWriter fileWriter = new FileWriter("/tmp/"
                    + providerParams.getSearchProviderName() + "_Coffee.json");
            fileWriter.write(results.toString());
            fileWriter.close();

            String assertMsg = "provider:" + providerParams.getSearchProviderName();
            Assert.assertEquals(assertMsg,
                    providerParams.getSearchProviderName(),
                    results.get("search_parameters").getAsJsonObject().get("engine").getAsString());
            JsonArray organicResults = results.getAsJsonArray("organic_results");
            Assert.assertTrue(assertMsg,organicResults.size() >= 1);
            JsonObject firstResult = organicResults.get(0).getAsJsonObject();
            Assert.assertNotNull(assertMsg, firstResult.get("title"));
        }
    }
}
