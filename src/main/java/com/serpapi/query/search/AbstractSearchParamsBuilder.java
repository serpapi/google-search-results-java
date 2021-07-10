package com.serpapi.query.search;

import com.serpapi.client.QueryParamConstants;
import com.serpapi.client.SearchParamsSupplier;
import com.serpapi.model.search.SearchType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.serpapi.client.QueryParamConstants.*;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/16/21
 * Time: 10:50 PM
 */
public abstract class AbstractSearchParamsBuilder implements SearchParamsSupplier {
    private final Map<String, String> params = new HashMap<>();

    public AbstractSearchParamsBuilder(String provider) {
        param(QueryParamConstants.PARAM_PROVIDER, provider);
    }

    public AbstractSearchParamsBuilder withSearchItem(String searchItem) {
        return param(getSearchParamName(), searchItem);
    }

    public AbstractSearchParamsBuilder withOffsetLimit(int offset, int limit) {
        return param(PARAM_START, String.valueOf(offset))
                .param(PARAM_NUM, String.valueOf(limit));
    }

    public AbstractSearchParamsBuilder withJsonOutput() {
        return param(PARAM_OUTPUT, "json");
    }

    public AbstractSearchParamsBuilder withHtmlOutput() {
        return param(PARAM_OUTPUT, "html");
    }

    public AbstractSearchParamsBuilder withLocation(String location) {
        return param(PARAM_LOCATION, location);
    }

    public AbstractSearchParamsBuilder withLanguage(String language) {
        return param(PARAM_LANGUAGE, language);
    }

    public AbstractSearchParamsBuilder withCountry(String country) {
        return param(PARAM_COUNTRY, country);
    }

    public AbstractSearchParamsBuilder withSearchType(SearchType searchType) {
        String paramVal = switch (searchType) {
            case IMAGE -> "isch";
            case VID -> "vid";
            case NEWS -> "nws";
            case LOCAL -> "lcl";
            case SHOP -> "shop";
            default -> null;
        };
        if (paramVal != null) {
            param(PARAM_SEARCH_TYPE, paramVal);
        }
        return this;
    }

    public AbstractSearchParamsBuilder clearParam(String paramName) {
        params.remove(paramName);
        return this;
    }

    private AbstractSearchParamsBuilder param(String paramName, String paramValue) {
        params.put(paramName, paramValue);
        return this;
    }

    public Map<String, String> build() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public Map<String, String> get() {
        return build();
    }

    public abstract String getSearchParamName();

    public abstract String getSearchProviderName();
}
