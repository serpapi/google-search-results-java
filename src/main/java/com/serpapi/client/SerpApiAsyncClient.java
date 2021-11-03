package com.serpapi.client;

import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.AccountResponse;
import com.serpapi.model.responses.LocationsResponse;
import com.serpapi.model.responses.SearchResponse;

import java.io.Closeable;

/**
 * The interface for an asynchronous client to talk to SerpApi.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/11/21
 * Time: 11:13 PM
 */
public interface SerpApiAsyncClient extends Closeable {
    String SERPAPI_BASE_URL = "https://serpapi.com";

    /**
     * Run a search and get the result. The various available query parameters are defined
     * in {@link QueryParamConstants}.
     *
     * @param paramsProvider a provider of the search query parameters
     * @param callback the async callback invoked on completion containing the response
     */
    void search(ApiKey apiKey, SearchParamsSupplier paramsProvider, AsyncCallback<SearchResponse> callback)
            throws SerpApiException;

    /**
     * Get the search result for a previously run search, using its {@code searchId}.
     *
     * @param searchId the id of a previously run and archived search
     * @param callback the async callback invoked on completion containing the response
     */
    void search(ApiKey apiKey, String searchId, AsyncCallback<SearchResponse> callback)
            throws SerpApiException;

    /**
     * Get one's account information.
     *
     * @param callback the async callback invoked on completion containing the response
     */
    void account(ApiKey apiKey, AsyncCallback<AccountResponse> callback) throws SerpApiException;

    /**
     * Get the list of available locations matching {@code locSubstr}. If {@code limit} is non-null, the results
     * are limited to those many items. This API does not need an {@link ApiKey}.
     *
     * @param locSubstr location substring to match. For e.g. Austin will find "Austin, TX",
     *                  "The University of Texas at Austin", "Rochester, MN-Mason City, IA-Austin, MN,United States",
     *                  etc.
     * @param limit if non-null and > 0, causes the output to be limited to those many entries. If 0 or negative,
     *              throws a SerpApiException
     * @param callback the async callback invoked on completion containing the response
     */
    void locations(String locSubstr, Integer limit, AsyncCallback<LocationsResponse> callback) throws SerpApiException;
}
