package com.serpapi.query.search;

import com.serpapi.SerpApiUnitTest;
import com.serpapi.client.SerpApiSyncClient;
import com.serpapi.exceptions.SerpApiException;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/15/21
 * Time: 8:28 AM
 */
public abstract class AbstractSearchTest extends SerpApiUnitTest {

    protected SerpApiSyncClient searchClient;

    @Before
    public void setUp() throws SerpApiException {
        searchClient = new SerpApiSyncClient();
    }

    @After
    public void testTeardown() throws IOException {
        searchClient.close();
    }
}
