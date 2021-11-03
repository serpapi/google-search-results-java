package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * DuckDuckGo search parameters builder
 */
public class DuckDuckGoSearchParamsBuilder extends AbstractSearchParamsBuilder {
    private static final String myProvider = PROVIDER_DUCKDUCKGO;

    public DuckDuckGoSearchParamsBuilder() {
        super(myProvider);
    }

    @Override
    public String getSearchParamName() {
        return PARAM_Q;
    }

    @Override
    public String getSearchProviderName() {
        return myProvider;
    }
}
