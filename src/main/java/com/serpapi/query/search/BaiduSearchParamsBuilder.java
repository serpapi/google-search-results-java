package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.PARAM_Q;
import static com.serpapi.client.QueryParamConstants.PROVIDER_BAIDU;

/**
 * Baidu search parameters builder
 */
public class BaiduSearchParamsBuilder extends AbstractSearchParamsBuilder {
  private static final String myProvider = PROVIDER_BAIDU;

  public BaiduSearchParamsBuilder() {
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
