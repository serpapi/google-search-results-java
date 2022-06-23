serpapi4j - the Java API to access SerpApi services
===

![test](https://github.com/serpapi/google-search-results-java/workflows/test/badge.svg)

This Java package enables the scraping and parsing of search results using [SerpApi](https://serpapi.com) from various
search providers like Google, Bing, Baidu, etc. Feel free to fork this repository to add more backends.

The default language version for this package is Java 16, but if a lower version package is required
feel free to contact me at my email below. This code depends on GSON for efficient JSON processing.
The HTTP responses are converted to JSON using GSON.

There are two native clients available to talk SerpApi. The asynchronous client provides callbacks
when search results are available. The synchronous client is written on top of the async client that 
blocks the thread until the results are available.

For an example usage either see the demo app, which uses the `ParamsBasedSearch`
class, at:
`src/main/java/com/serpapi/demo/App.java`
or see the unit test, which uses the SerpApiSyncClient directly, at:
`src/test/java/com/serpapi/query/search/SearchParamsTest.java`

For understanding how to use `SerpApiAsyncClient`, see the implementation of `SerpApiSyncClient`.
The APIs in `SerpApiAsyncClient/SerpApiSyncClient` are fairly self-explanatory.

[The full documentation for SerpApi is available here.](https://serpapi.com/search-api)

## Requirements

Runtime:
 - Java / JDK 16+ (https://www.java.com/en/download/)

For development:
 - Maven 3.8.1 (on a Mac)

## Quick start

To get started with this project in Java. 
We provide a fully working example.
```bash
git clone git@github.com:rumpelstiltzkin/serpapi4j.git
cd serpapi4j/
SERPAPI_KEY="<your private serpapi key>" mvn clean install
```
Note: You need an account with SerpApi to obtain this private key from: https://serpapi.com/dashboard

file: `src/main/java/com/serpapi/demo/App.java`
```java
public class App {
    public static void main(String[] args) throws SerpApiException {
        if (args.length != 1) {
            System.out.println("Usage: app <serp api key>");
            System.exit(1);
        }

        // Create a search against a provider like Google using your API-key.
        ParamsBasedSearch paramsBasedSearch = new ParamsBasedSearch(new ApiKey(args[0]));
        GoogleSearchParamsBuilder googleSearchParams = new GoogleSearchParamsBuilder();

        String location = "Austin,Texas";
        System.out.println("find the first Coffee in " + location);
        // parameters
        googleSearchParams.withSearchItem("Coffee").withLocation(location);

        try {
            // Execute search
            SearchResponse searchResponse = paramsBasedSearch.getResult(googleSearchParams);
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

This example runs a search for "Coffee" using your SerpApi key.

The SerpApi service (running at serpapi.com)
 - searches on Google using the query: q = "coffee"
 - parses the HTML responses
 - return a standardized JSON response

The classes `ParamsBasedSearch` or `SerpApi*Client`:
 - Format the request to the SerpApi server
 - Execute a GET http request
 - Parse the returned JSON response using Gson
to provide the results whose individual fields can be parsed out of the JSON response.

To use different search providers like Bing, DuckDuckGo, etc. simply use the appropriate
search params builder (e.g. `DuckDuckGoSearchParamsBuilder`).

See the playground to generate your code.
 https://serpapi.com/playground

## Examples
 * [How to use your SerpApi key](#how-to-use-your-SerpApi-key)
 * [Possible search params](#possible-search-params)
 * [The Location API](#the-location-api)
 * [The Archive search API](#the-archive-search-api)
 * [The Account API](#the-account-api)

### How to use your SerpApi key
The SerpApi key can be used with the client for the APIs that need the key
```java
GoogleSearchParamsBuilder searchParams = new GoogleSearchParamsBuilder();
searchParams.withSearchItem("Coffee");
SearchResponse searchResponse = searchClient.search(new ApiKey("<your api key>"), searchParams);
```

### Possible search params
These are all the possible search params to SerpApi. Not all are implemented.
Feel free to fork this repo and add methods to AbstractSearchParamsBuilder.

```less
withSearchItem(String searchItem) // e.g. "coffee"
withOffsetLimit(int offset, int limit) // specify start offset and limit in results
withJsonOutput() / withHtmlOutput() // control output format
withLocation(String location) // e.g. "Austin, TX"
withLanguage(String language) // e.g. "en"
withCountry(String country) // e.g. "us"
withSearchType(SearchType searchType) // e.g. IMAGE, NEWS, SHOP, etc.
withAdvancedSearchParams(AdvancedSearchParams advSearchParams) // e.g. IMGSZ_MEDIUM
```

### The Location API
```java
SerpApiSyncClient client = new SerpApiSyncClient();
LocationsResponse locationsResponse = client.locations("San Jose", 3);
JsonArray locations = locationsResponse.getJsonArray();
Assert.assertEquals(3, locations.size());
client.close();
```
This gets the first 3 locations matching "San Jose".

### The Archive search API
Let's run a search to get a search_id and then use that search_id to get results from the 
search archive maintained by serpapi.com.
```java
// First run a params-based search to get an Id.
GoogleSearchParamsBuilder paramsBuilder = new GoogleSearchParamsBuilder(); // use google for this
paramsBuilder.withSearchItem("Coffee");
SearchResponse searchResponse = searchClient.search(getTestApiKey(), paramsBuilder);
JsonObject paramsBasedResults = searchResponse.getJsonObject();
Assert.assertTrue(paramsBasedResults.getAsJsonArray("organic_results").size() >= 1);

// Then search again with the search id to get it from the archive
String searchId = paramsBasedResults.get("search_metadata").getAsJsonObject().get("id").getAsString();
SearchResponse searchResponse = searchClient.search(getTestApiKey(), searchId);
JsonObject archivedResult = searchResponse.getJsonObject();
Assert.assertEquals(searchId, archivedResult.get("search_metadata").getAsJsonObject().get("id").getAsString());
Assert.assertEquals(paramsBasedResults, archivedResult);
```

### The Account API
This prints your account information.
```java
SerpApiSyncClient client = new SerpApiSyncClient();
AccountResponse accountResponse = client.account(getTestApiKey());
JsonObject account = accountResponse.getJsonObject();
Assert.assertEquals(getTestApiKey(), new ApiKey(account.get("api_key").getAsString()));
client.close();
```

### How to build from the source
You must clone this repository.
```bash
$> git clone https://github.com/rumpelstiltzkin/serpapi4j.git
```
Build the jar file.
```bash
$> SERPAPI_KEY="<your private serpapi key>" mvn clean install
```
Copy the jar to your project lib/ directory.
```bash
$> cp target/serpapi4j-2.0.1-SNAPSHOT.jar path/to/yourproject/lib
```

### How to test
```bash
$> SERPAPI_KEY="<your private serpapi key>" mvn clean install
```

### Conclusion

This service supports searching of images, news articles, shopping results, etc.
The full documentation and latest search parameters are listed on the [SerpApi website](https://serpapi.com/search-api).

Issues
---
None

Changelog
---
- 2.0.3-SNAPSHOT Updated this README to be more correct
- 2.0.2-SNAPSHOT serpapi4j
- 2.0.1 update gradle 6.7.1 
- 2.0 refactor API : suffix SearchResults renamed Search
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
