package com.serpapi.query.search;

import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.SearchResponse;
import com.serpapi.query.AbstractSerpApiQuery;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/14/21
 * Time: 7:07 PM
 */
public abstract class AbstractSerpApiSearch<P> extends AbstractSerpApiQuery<SearchResponse, P> {
    protected final ApiKey apiKey;

    public AbstractSerpApiSearch(ApiKey apiKey) throws SerpApiException {
        super();
        this.apiKey = apiKey;
    }

    public abstract SearchResponse getResult(P param) throws SerpApiException;
}
