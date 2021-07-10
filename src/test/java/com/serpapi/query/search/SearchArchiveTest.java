package com.serpapi.query.search;

import com.google.gson.JsonObject;
import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.responses.SearchResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/15/21
 * Time: 5:28 PM
 */
public class SearchArchiveTest extends AbstractSearchTest {

    @Test
    public void searchCoffeeBySearchId() throws SerpApiException {

        // First run a params-based search to get an Id.
        GoogleSearchParamsBuilder paramsBuilder = new GoogleSearchParamsBuilder(); // use google for this
        paramsBuilder.withSearchItem("Coffee");
        SearchResponse searchResponse = searchClient.search(getTestApiKey(), paramsBuilder);
        JsonObject paramsBasedResults = searchResponse.getJsonObject();
        Assert.assertTrue(paramsBasedResults.getAsJsonArray("organic_results").size() > 5);

        String searchId = paramsBasedResults.get("search_metadata").getAsJsonObject().get("id").getAsString();
        JsonObject archivedResult = searchClient.search(getTestApiKey(), searchId).getJsonObject();
        Assert.assertEquals(searchId, archivedResult.get("search_metadata").getAsJsonObject().get("id").getAsString());
        Assert.assertEquals(paramsBasedResults, archivedResult);
    }
}
