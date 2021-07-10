package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * Bing search parameters builder
 */
public class BingSearchParamsBuilder extends AbstractSearchParamsBuilder {
  private static final String myProvider = PROVIDER_BING;

  public BingSearchParamsBuilder() {
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
