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

## Quick start

You must clone this repository.
```bash
git clone https://github.com/serpapi/google_search_results_java.git
```
Build the jar file.
```bash
gradle build
```
Copy the jar to your project lib/ directory.
```bash
cp build/libs/google_search_results_java.jar path/to/yourproject/lib
```

Note: we are looking at better solution.

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

This example runs a search about "coffee" using your secret api key.

The Serp API service (backend)
 - searches on Google using the query: q = "coffee"
 - parses the messy HTML responses
 - return a standardizes JSON response
The Ruby class GoogleSearchResults
 - Format the request to Serp API server
 - Execute GET http request
 - Parse JSON into Ruby Hash using JSON standard library provided by Ruby
Et voila..

## Example
 * [How to set SERP API key](#how-to-set-serp-api-key)
 * [Search API capability](#search-api-capability)
 * [Example by specification](#example-by-specification)
 * [Location API](#location-api)
 * [Search Archive API](#search-archive-api)
 * [Account API](#account-api)

## How to set SERP API key
The Serp API key can be set globally using a singleton pattern.
```java
GoogleSearchResults.serp_api_key_default = "Your Private Key"
query = GoogleSearchResults(parameter)
```
Or the Serp API key can be provided for each query.

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
  "serp_api_key": "Your SERP API Key",
  "tbm": "nws|isch|shop"
  "tbs": "custom to be search criteria"
  "async": true|false # allow async 
  "output": "json|html" # output format
}

query = GoogleSearchResults.new(query_parameter)
query.parameter.put("location", "Austin,Texas")

String html_results = query.getHtml()
JsonObject json_results = query.getJson()
```

### Example by specification

We love true open source, continuous integration and Test Drive Development (TDD). 
 We are using RSpec to test [our infrastructure around the clock](https://travis-ci.org/serpapi/google-search-results-ruby) to achieve the best QoS (Quality Of Service).
 
The directory test/ includes specification/examples.

To run the test:
```gradle test```


### Location API

```java
GoogleSearchResults client = new GoogleSearchResults(new HashMap<String, String());
JsonArray locationList = client.getLocation("Austin", 3);
System.out.println(locationList.toString());
```
it prints the first 3 location matching Austin (Texas, Texas, Rochester)

### Search Archive API

Let's run a search to get a search_id.
TODO write java code
```ruby
search = GoogleSearchResults.new(q: "Coffee", location: "Portland")
original_search = search.get_hash
search_id = original_search[:search_metadata][:id]

Now let retrieve the previous search from the archive.
```ruby
search = GoogleSearchResults.new
archive_search = search.get_search_archive(search_id)
pp archive_search
```
it prints the search from the archive.

### Account API
TODO write java code
```ruby
search = GoogleSearchResults.new
pp search.get_account
```
it prints your account information.


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

Issue
---
 - SerpAPI is using HTTPS / SSLv3. The older version of Java are not supporting this protocol. You must upgrade to 1.8_201+

For example: To switch
```sh
export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_201`
java -version
```


Source
---
 * http://www.baeldung.com/java-http-request
 * https://github.com/google/gson

Author
---
Victor Benarbia
