package com.serpapi.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.SearchResponse;
import com.serpapi.query.search.GoogleSearchParamsBuilder;
import com.serpapi.query.search.ParamsBasedSearch;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws SerpApiException {
        if (args.length != 1) {
            System.out.println("Usage: app <serp api key>");
            System.exit(1);
        }

        // Create a search against a provider like Google using your API-key.
        ParamsBasedSearch paramsBasedSearch = new ParamsBasedSearch(new ApiKey(args[0]));
        GoogleSearchParamsBuilder googleSearchParams = new GoogleSearchParamsBuilder();

        String location = "Austin,Texas";
        System.out.println("find the first Coffee in " + location);
        // parameters
        googleSearchParams.withSearchItem("Coffee").withLocation(location);

        try {
            // Execute search
            SearchResponse searchResponse = paramsBasedSearch.getResult(googleSearchParams);
            // Decode response
            JsonArray results = searchResponse.getJsonObject()
                    .get("local_results").getAsJsonObject()
                    .get("places").getAsJsonArray();
            JsonObject first_result = results.get(0).getAsJsonObject();
            System.out.println("first coffee shop: " + first_result.get("title").getAsString() + " found on Google in " + location);
            paramsBasedSearch.close();
        } catch (SerpApiException | IOException exception) {
            System.out.println(exception.getMessage() + " - while performing search");
            exception.printStackTrace();
        }

        System.exit(0);
    }
}
