package com.serpapi.query.search;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * Yandex search parameters builder
 */
public class YandexSearchParamsBuilder extends AbstractSearchParamsBuilder {
  private static final String myProvider = PROVIDER_YANDEX;

  public YandexSearchParamsBuilder() {
    super(myProvider);
  }

  @Override
  public String getSearchParamName() {
    return PARAM_TEXT;
  }

  @Override
  public String getSearchProviderName() {
    return myProvider;
  }
}
