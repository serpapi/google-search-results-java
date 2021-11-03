package com.serpapi.query.search;

import com.serpapi.client.SearchParamsSupplier;
import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.SearchResponse;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/15/21
 * Time: 9:12 PM
 */
public class ParamsBasedSearch extends AbstractSerpApiSearch<SearchParamsSupplier> {

    public ParamsBasedSearch(ApiKey apiKey) throws SerpApiException {
        super(apiKey);
    }

    @Override
    public SearchResponse getResult(SearchParamsSupplier searchParamsSupplier) throws SerpApiException {
        return client.search(apiKey, searchParamsSupplier);
    }
}
