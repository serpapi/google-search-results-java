package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * Google search parameters builder
 */
public class GoogleSearchParamsBuilder extends AbstractSearchParamsBuilder {
  private static final String myProvider = PROVIDER_GOOGLE;

  public GoogleSearchParamsBuilder() {
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
