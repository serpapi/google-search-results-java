package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * Ebay search parameters builder
 */
public class EbaySearchParamsBuilder extends AbstractSearchParamsBuilder {
  private static final String myProvider = PROVIDER_EBAY;

  public EbaySearchParamsBuilder() {
    super(myProvider);
  }

  @Override
  public String getSearchParamName() {
    return PARAM_NKW;
  }

  @Override
  public String getSearchProviderName() {
    return myProvider;
  }
}
