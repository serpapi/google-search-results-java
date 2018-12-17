Google Search Results JAVA API
===

[![Build Status](https://travis-ci.org/serpapi/google-search-results-java.svg?branch=master)](https://travis-ci.org/serpapi/google-search-results-java)

This Java package enables to scrape and parse Google search results using [SerpApi](https://serpapi.com). Feel free to fork this repository to add more backends.

This project is an implementation of SerpApi in Java 7.
This code depends on GSON for efficient JSON processing.
The HTTP response are converted to JSON Object.

An example is provided in the test.
@see src/test/java/GoogleSearchResultsImplementationTest.java

[The full documentation is available here.](https://serpapi.com/search-api)

## Simple Example
```java
Map<String, String> parameter = new HashMap<>();
parameter.put("q", "Coffee");
parameter.put("location", "Austin,Texas");
parameter.put(GoogleSearchResults.SERP_API_KEY_NAME, "demo");
GoogleSearchResults serp = new GoogleSearchResults(parameter);

JsonObject data = serp.getJson();
JsonArray results = (JsonArray) data.get("local_results");
JsonObject first_result = results.get(0).getAsJsonObject();
System.out.println("first coffee: " + first_result.get("title").getAsString());
```

## Set parameter
```java
Map<String, String> parameter = new HashMap<>();
parameter.put("q", "Coffee");
parameter.put("location", "Portland");
```

## Set SERP API key

```java
GoogleSearchResults.serp_api_key_default = "Your Private Key"
query = GoogleSearchResults(parameter)
```
Or

```java
query = GoogleSearchResults(parameter, "Your Private Key")
```

## Example with all params and all outputs

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
  "serp_api_key": "Your SERP API Key"
}

query = GoogleSearchResults.new(query_parameter)
query.parameter.put("location", "Austin,Texas")

String html_results = query.getHtml()
JsonObject json_results = query.getJson()
```

This service supports Google Images, News, Shopping.
To enable a type of search, the field tbm (to be matched) must be set to:

 * isch: Google Images API.
 * nws: Google News API.
 * shop: Google Shopping API.
 * any other Google service should work out of the box.
 * (no tbm parameter): regular Google Search.

[The full documentation is available here.](https://serpapi.com/search-api)

Limitation
---
 - No wrapper method around parametrization.
  the parameters are passed by a simple Map<String,String>

Source
---
 * http://www.baeldung.com/java-http-request
 * https://github.com/google/gson

Author
---
Victor Benarbia
