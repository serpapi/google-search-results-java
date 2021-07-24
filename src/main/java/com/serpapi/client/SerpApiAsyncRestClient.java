package com.serpapi.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.serpapi.exceptions.SerpApiException;
import com.serpapi.model.ApiKey;
import com.serpapi.model.responses.AccountResponse;
import com.serpapi.model.responses.ErrorResponse;
import com.serpapi.model.responses.LocationsResponse;
import com.serpapi.model.responses.SearchResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An implementation of {@link SerpApiAsyncClient}.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/11/21
 * Time: 11:52 PM
 */
@Log4j2
public class SerpApiAsyncRestClient implements SerpApiAsyncClient {
    private static final int CONN_MGR_MAX_TOTAL_CONNECTIONS = 5;
    private static final int CONN_MGR_MAX_PER_ROUTE_CONNECTIONS = 5;
    private static final long CONNECT_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(30);
    private static final long READ_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(2);

    private static final String PARAM_API_KEY = "api_key"; // to supply the API key to use SerpApi
    private static final String PARAM_SOURCE_LANGUAGE = "source"; // e.g. "java", "python", etc.
    private static final String SOURCE_LANGUAGE = "java"; // this library is written in java

    private static final AtomicBoolean TRUST_ALL_HAS_BEEN_SETUP = new AtomicBoolean(false);

    private final Client javaxClient; // as opposed to a jakarta (ws.rs.) client
    private final ExecutorService es; // a thread pool to run the async responses in

    public SerpApiAsyncRestClient() throws SerpApiException {
        es = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder()
                .setUncaughtExceptionHandler((thread, throwable) ->
                        log.fatal("uncaught exception {} on thread {}", throwable.getMessage(), thread, throwable))
                .setNameFormat("rest-client-%d")
                .build());

        // client configuration
        ClientConfig config = new ClientConfig();
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(CONN_MGR_MAX_TOTAL_CONNECTIONS);
        connMgr.setDefaultMaxPerRoute(CONN_MGR_MAX_PER_ROUTE_CONNECTIONS);
        config.property(ApacheClientProperties.CONNECTION_MANAGER, connMgr);
        config.connectorProvider(new ApacheConnectorProvider());

        this.javaxClient = ClientBuilder.newBuilder()
                .withConfig(config)
                .executorService(es)
                .register(JsonProcessingFeature.class)
                .register(LoggingFeature.class)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_NAME_CLIENT, log.getName())
                .connectTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .build();

        if (TRUST_ALL_HAS_BEEN_SETUP.compareAndSet(false, true)) {
            // setup 'trust all' only once in the life of the application running this library
            try {
                trustAllHttps();
            } catch (NoSuchAlgorithmException|KeyManagementException exc) {
                throw new SerpApiException(exc);
            }
        }
    }

    private static void trustAllHttps() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts;
        trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    private static MediaType getMediaType(Map<String, String> params)
            throws SerpApiException {
        String outputFormat = params.get(QueryParamConstants.PARAM_OUTPUT);
        if (outputFormat == null || outputFormat.equals("json")) {
            return MediaType.APPLICATION_JSON_TYPE;
        } else if (outputFormat.equals("html")) {
            return MediaType.TEXT_HTML_TYPE;
        } else {
            throw new SerpApiException(new IllegalArgumentException("invalid outputFormat=" + outputFormat));
        }
    }

    @Override
    public void search(ApiKey apiKey, SearchParamsSupplier paramsProvider, AsyncCallback<SearchResponse> callback)
            throws SerpApiException {
        try {
            Map<String, String> params = paramsProvider.get();
            MediaType mediaType = getMediaType(params);
            WebTarget webTarget = javaxClient.target(SERPAPI_BASE_URL)
                    .path("search")
                    .queryParam(PARAM_API_KEY, apiKey.key())
                    .queryParam(PARAM_SOURCE_LANGUAGE, SOURCE_LANGUAGE);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
            webTarget.request(mediaType)
                    .async()
                    .get(new InvocationCallback<Response>() {
                        @Override
                        public void completed(Response response) {
                            int httpStatus = response.getStatus();
                            String responseBody = response.readEntity(String.class);
                            if (httpStatus == HttpStatus.SC_OK) {
                                if (mediaType == MediaType.APPLICATION_JSON_TYPE) {
                                    callback.completed(SearchResponse.forJson(responseBody));
                                } else {
                                    assert mediaType == MediaType.TEXT_HTML_TYPE : "unknown mediaType=" + mediaType;
                                    callback.completed(SearchResponse.forHtml(responseBody));
                                }
                            } else {
                                callback.failed(ErrorResponse.from(responseBody, httpStatus, params.toString()));
                            }
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            callback.failed(throwable);
                        }
                    });
        } catch (Throwable throwable) {
            throw new SerpApiException(throwable);
        }
    }

    @Override
    public void search(ApiKey apiKey, String searchId, AsyncCallback<SearchResponse> callback)
            throws SerpApiException {
        try {
            javaxClient.target(SERPAPI_BASE_URL)
                    // "/searches/" + searchID + ".json"
                    .path("searches")
                    .path("{searchId}.json")
                    .resolveTemplate("searchId", searchId)
                    .queryParam(PARAM_API_KEY, apiKey.key())
                    .queryParam(PARAM_SOURCE_LANGUAGE, SOURCE_LANGUAGE)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .async()
                    .get(new InvocationCallback<Response>() {
                        @Override
                        public void completed(Response response) {
                            int httpStatus = response.getStatus();
                            String responseBody = response.readEntity(String.class);
                            if (httpStatus == HttpStatus.SC_OK) {
                                callback.completed(SearchResponse.forJson(responseBody));
                            } else {
                                callback.failed(ErrorResponse.from(responseBody, httpStatus, "searchId=" + searchId));
                            }
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            callback.failed(throwable);
                        }
                    });
        }  catch (Throwable throwable) {
            throw new SerpApiException(throwable);
        }
    }

    @Override
    public void account(ApiKey apiKey, AsyncCallback<AccountResponse> callback)
            throws SerpApiException {
        try {
            javaxClient.target(SERPAPI_BASE_URL)
                    .path("account")
                    .queryParam(PARAM_API_KEY, apiKey.key())
                    .queryParam(PARAM_SOURCE_LANGUAGE, SOURCE_LANGUAGE)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .async()
                    .get(new InvocationCallback<Response>() {
                        @Override
                        public void completed(Response response) {
                            int httpStatus = response.getStatus();
                            String responseBody = response.readEntity(String.class);
                            if (httpStatus == HttpStatus.SC_OK) {
                                callback.completed(AccountResponse.from(responseBody));
                            } else {
                                callback.failed(ErrorResponse.from(responseBody, httpStatus, "account info"));
                            }
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            callback.failed(throwable);
                        }
                    });
        }  catch (Throwable throwable) {
            throw new SerpApiException(throwable);
        }
    }

    @Override
    public void locations(String locSubstr, Integer limit, AsyncCallback<LocationsResponse> callback)
            throws SerpApiException {
        try {
            // do not add the SERPAPI_KEY_NAME
            WebTarget webTarget = javaxClient.target(SERPAPI_BASE_URL)
                    .path("locations.json")
                    .queryParam(PARAM_SOURCE_LANGUAGE, SOURCE_LANGUAGE)
                    .queryParam(QueryParamConstants.PARAM_Q, locSubstr);
            if (limit != null && limit > 0) {
                webTarget.queryParam(QueryParamConstants.PARAM_LIMIT, String.valueOf(limit));
            }
            webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .async()
                    .get(new InvocationCallback<Response>() {
                        @Override
                        public void completed(Response response) {
                            int httpStatus = response.getStatus();
                            String responseBody = response.readEntity(String.class);
                            if (httpStatus == HttpStatus.SC_OK) {
                                callback.completed(LocationsResponse.from(responseBody));
                            } else {
                                callback.failed(ErrorResponse.from(responseBody, httpStatus, "locations"));
                            }
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            callback.failed(throwable);
                        }
                    });
        }  catch (Throwable throwable) {
            throw new SerpApiException(throwable);
        }
    }

    @Override
    public void close() {
        javaxClient.close();
        es.shutdown();
    }
}
