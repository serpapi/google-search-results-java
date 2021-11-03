package com.serpapi.query;

import com.serpapi.client.SerpApiSyncClient;
import com.serpapi.exceptions.SerpApiException;

import java.io.Closeable;
import java.io.IOException;

/**
 * A SerpApiQuery that takes P as a parameter and returns R as the result.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/14/21
 * Time: 11:47 PM
 */
public abstract class AbstractSerpApiQuery<R, P> implements Closeable {
    protected final SerpApiSyncClient client;

    protected AbstractSerpApiQuery() throws SerpApiException {
        client = new SerpApiSyncClient();
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    public abstract R getResult(P param) throws SerpApiException;
}
