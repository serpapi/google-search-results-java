package com.serpapi.client;

import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.AccountResponse;
import com.serpapi.model.responses.LocationsResponse;
import com.serpapi.model.responses.SearchResponse;

import java.io.Closeable;
import java.io.IOException;

/**
 * The synchronous version of the {@link SerpApiAsyncClient} interface.
 * For documentation refer to {@link SerpApiAsyncClient}.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/14/21
 * Time: 7:10 PM
 */
public class SerpApiSyncClient implements Closeable {
    private final SerpApiAsyncClient asyncClient;

    public SerpApiSyncClient() throws SerpApiException {
        asyncClient = new SerpApiAsyncRestClient();
    }

    public SearchResponse search(ApiKey apiKey, SearchParamsSupplier paramsProvider)
            throws SerpApiException {
        AsyncToSync<SearchResponse> asyncToSync = new AsyncToSync<>();
        asyncClient.search(apiKey, paramsProvider, asyncToSync);
        return asyncToSync.waitForCompletion();
    }

    public SearchResponse search(ApiKey apiKey, String searchId)
            throws SerpApiException {
        AsyncToSync<SearchResponse> asyncToSync = new AsyncToSync<>();
        asyncClient.search(apiKey, searchId, asyncToSync);
        return asyncToSync.waitForCompletion();
    }

    public AccountResponse account(ApiKey apiKey)
            throws SerpApiException {
        AsyncToSync<AccountResponse> asyncToSync = new AsyncToSync<>();
        asyncClient.account(apiKey, asyncToSync);
        return asyncToSync.waitForCompletion();
    }

    public LocationsResponse locations(String locSubstr, Integer limit)
            throws SerpApiException {
        AsyncToSync<LocationsResponse> asyncToSync = new AsyncToSync<>();
        asyncClient.locations(locSubstr, limit, asyncToSync);
        return asyncToSync.waitForCompletion();
    }

    @Override
    public void close() throws IOException {
        asyncClient.close();
    }
}
