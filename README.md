serpapi4j - the Java API to access SerpApi services
===

![test](https://github.com/serpapi/google-search-results-java/workflows/test/badge.svg)

This Java package enables to scrape and parse search results using [SerpApi](https://serpapi.com) from various
search providers like Google, Bing, Baidu, etc. Feel free to fork this repository to add more backends.

The default language version for this package is Java 16, but if a lower version package is required
feel free to contact me at my email below. This code depends on GSON for efficient JSON processing.
The HTTP responses are converted to JSON using GSON.

There are two native clients available to talk SerpApi. The asynchronous client provides callbacks
when search results are available. The synchronous client is written on top of the async client that 
blocks the thread until the results are available.

For an example usage, either see the demo App, which uses the ParamsBasedSearch class, at:
src/main/java/com/serpapi/demo/App.java
or see the unit test, which uses the SerpApiSyncClient directly, at:
src/test/java/com/serpapi/query/search/SearchParamsTest.java

For understanding how to use SerpApiAsyncClient, see the implementation of SerpApiSyncClient.
The APIs in SerpApiAsyncClient/SerpApiSyncClient are fairly self-explanatory.

[The full documentation for SerpApi is available here.](https://serpapi.com/search-api)

## Requirements

Runtime:
 - Java / JDK 8+ (https://www.java.com/en/download/)
   Older version of java do not support HTTPS protocol. 
   The SSLv3 is buggy which leads to Java raising this exception: javax.net.ssl.SSLHandshakeException

For development:
 - Maven 3.8.1 (on a Mac)

## Quick start

To get started with this project in Java. 
We provided a fully working example.
```bash
git clone https://github.com/rumpelstiltzkin/google-search-results-java
cd google_search_results_java/
SERPAPI_KEY="<your serpapi key>" mvn clean install
```
Note: You need an account with SerpApi to obtain this key from: https://serpapi.com/dashboard

file: src/main/java/com/serpapi/demo/App.java
```java
public class App {
    public static void main(String[] args) throws SerpApiException {
        if (args.length != 1) {
            System.out.println("Usage: app <serp api key>");
            System.exit(1);
        }

        // Create a search against a provider like Google using your API-key.
        ParamsBasedSearch paramsBasedSearch = new ParamsBasedSearch(new ApiKey(args[0]));
        GoogleSearchParamsBuilder paramsBuilder = new GoogleSearchParamsBuilder();

        String location = "Austin,Texas";
        System.out.println("find the first Coffee in " + location);
        // parameters
        paramsBuilder.withSearchItem("Coffee").withLocation(location);

        try {
            // Execute search
            SearchResponse searchResponse = paramsBasedSearch.getResult(paramsBuilder);
            // Decode response
            JsonArray results = searchResponse.getJsonObject()
                    .get("local_results").getAsJsonObject()
                    .get("places").getAsJsonArray();
            JsonObject first_result = results.get(0).getAsJsonObject();
            System.out.println("first coffee shop: " + first_result.get("title").getAsString() + " found on Google in " + location);
            paramsBasedSearch.close();
        } catch (SerpApiException | IOException exception) {
            System.out.println(exception.getMessage() + " - while performing search");
            exception.printStackTrace();
        }

        System.exit(0);
    }
}
```

This example runs a search for "Coffee" using your serp api key.

The Serp API service (backend)
 - searches on Google using the query: q = "coffee"
 - parses the HTML responses
 - return a standardized JSON response

The classes ParamsBasedSearch or SerpApi*Client:
 - Format the request to SerpApi server
 - Execute a GET http request
 - Parse the JSON using Gson
to provide the results whose individual fields can be parsed out of the JSON.

To use different search providers like Bing, DuckDuckGo, etc. simply use the appropriate
SearchParamsBuilder (e.g. DuckDuckGoSearchParamsBuilder).

See the playground to generate your code.
 https://serpapi.com/playground

## Example
 * [How to set SERP API key](#how-to-set-serp-api-key)
 * [Search API capability](#search-api-capability)
 * [Example by specification](#example-by-specification)
 * [Location API](#location-api)
 * [Search Archive API](#search-archive-api)
 * [Account API](#account-api)

## How to use your SerpApi key
The SerpApi key can be used with the client for the APIs that need the key
```java
GoogleSearchParamsBuilder params = new GoogleSearchParamsBuilder();
params.withSearchItem("Coffee");
SearchResponse searchResponse = searchClient.search(new ApiKey("<your api key>"), providerParams);
```

## Possible search params
These are all the possible params to SerApi. Not all are implemented.
Feel free to fork this repo and add methods to AbstractSearchParamsBuilder.

```java
query_parameter = {
  "q": "query",
  "google_domain": "Google Domain",
  "location": "Location Requested",
  "device": device,
  "hl": "Google UI Language",
  "gl": "Google Country",
  "safe": "Safe Search Flag",
  "num": "Number of Results",
  "start": "Pagination Offset",
  "serp_api_key": "Your SERP API Key",
  "tbm": "nws|isch|shop",
  "tbs": "custom to be search criteria",
  "async": true|false,    // allow async request - non-blocker
  "output": "json|html",  // output format
}
```

### Location API

```java
SerpApiSyncClient client = new SerpApiSyncClient();
LocationsResponse locationsResponse = client.locations("San Jose", 3);
JsonArray locations = locationsResponse.getJsonArray();
Assert.assertEquals(10, locations.size());
client.close();
```
This gets the first 3 locations matching "San Jose".

### Search Archive API

Let's run a search to get a search_id.
```java
// First run a params-based search to get an Id.
GoogleSearchParamsBuilder paramsBuilder = new GoogleSearchParamsBuilder(); // use google for this
paramsBuilder.withSearchItem("Coffee");
SearchResponse searchResponse = searchClient.search(getTestApiKey(), paramsBuilder);
JsonObject paramsBasedResults = searchResponse.getJsonObject();
Assert.assertTrue(paramsBasedResults.getAsJsonArray("organic_results").size() > 5);

// Then search again with the search id to get it from the archive
String searchId = paramsBasedResults.get("search_metadata").getAsJsonObject().get("id").getAsString();
JsonObject archivedResult = searchClient.search(getTestApiKey(), searchId).getJsonObject();
Assert.assertEquals(searchId, archivedResult.get("search_metadata").getAsJsonObject().get("id").getAsString());
Assert.assertEquals(paramsBasedResults, archivedResult);
```

### Account API
Get account API
```java
SerpApiSyncClient client = new SerpApiSyncClient();
AccountResponse accountResponse = client.account(getTestApiKey());
JsonObject account = accountResponse.getJsonObject();
Assert.assertEquals(getTestApiKey(), new ApiKey(account.get("api_key").getAsString()));
client.close();
```
This prints your account information.

## Build project
```
$> mvn clean install
```

### How to build from the source ?

You must clone this repository.
```bash
$> git clone https://github.com/rumpelstiltzkin/google-search-results-java
```
Build the jar file.
```bash
$> mvn clean install
```
Copy the jar to your project lib/ directory.
```bash
$> cp target/serpapi4j-2.0.1-SNAPSHOT.jar path/to/yourproject/lib
```

### How to test ?

```bash
$> mvn clean install
```

### Conclusion

This service supports Google Images, News, Shopping.
To enable a type of search, the field tbm (to be matched) must be set to:

 * isch: Google Images API.
 * nws: Google News API.
 * shop: Google Shopping API.
 * any other Google service should work out of the box.
 * (no tbm parameter): regular Google Search.

[The full documentation is available here.](https://serpapi.com/search-api)

Issue
---
### SSL handshake error.

#### symptom

javax.net.ssl.SSLHandshakeException

#### cause
SerpApi is using HTTPS / SSLv3. Older JVM version do not support this protocol because it's more recent. 

#### solution
Upgrade java to 1.8_201+ (which is recommended by Oracle).

 * On OSX you can switch versino of Java.
```sh
export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_201`
java -version
```

 * On Windows manually upgrade your JDK / JVM to the latest.

 * On Linux, Oracle JDK 8 (1.8_151+) seems to work fine.
see: https://travis-ci.org/serpapi/google-search-results-java

Changelog
---
- 2.0.2-SNAPSHOT serpapi4j
- 2.0.1 update gradle 6.7.1 
- 2.0 refractor API : suffix SearchResults renamed Search
- 1.4 Add support for Yandex, Yahoo, Ebay
- 1.3 Add support for Bing and Baidu
- 1.2 Add support for location API, account API, search API

Source
---
 * https://www.baeldung.com/jersey-jax-rs-client
 * https://github.com/google/gson

Author
---
Anand Ganesh - rumpelgit@gmail.com. 

Forked and modified from the work done by Victor Benarbia - victor@serpapi.com
