package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * Yahoo search parameters builder
 */
public class YahooSearchParamsBuilder extends AbstractSearchParamsBuilder {
  private static final String myProvider = PROVIDER_YAHOO;

  public YahooSearchParamsBuilder() {
    super(myProvider);
  }

  @Override
  public String getSearchParamName() {
    return PARAM_P;
  }

  @Override
  public String getSearchProviderName() {
    return myProvider;
  }
}
