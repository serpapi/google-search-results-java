package com.serpapi.client;

/**
 * The recognized parameters to a query to SerpApi.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/11/21
 * Time: 10:27 AM
 */
public class QueryParamConstants {
    public static final String PARAM_Q = "q"; // the item to search for like "coffee"
    public static final String PARAM_P = "p"; // the item to search for like "coffee"
    public static final String PARAM_TEXT = "text"; // the item to search for like "coffee"
    public static final String PARAM_NKW = "_nkw"; // the item to search for like "coffee"

    public static final String PARAM_START = "start"; // start offset for search results
    public static final String PARAM_NUM = "num"; // num results
    public static final String PARAM_LIMIT = "limit"; // num results, for the /locations API
    public static final String PARAM_OUTPUT = "output"; // output format like "json", "html", etc.
    public static final String PARAM_PROVIDER = "engine"; // e.g. "google", "baidu", etc.
    public static final String PARAM_LOCATION = "location"; // e.g. "united+states", etc.
    public static final String PARAM_LANGUAGE = "hl"; // e.g. "en", etc.
    public static final String PARAM_COUNTRY = "gl"; // e.g. "us", etc.
    public static final String PARAM_SEARCH_TYPE = "tbm"; // e.g. "shop", "vid", etc.
    public static final String PARAM_ADVANCED_SEARCH_PARAMS = "tbs"; // applies to images
    public static final String PARAM_ADVANCED_SEARCH_IMAGE_SIZE_MEDIUM = "isz:m";
    public static final String PARAM_ADVANCED_SEARCH_IMAGE_LICENSE_COMMON = "il:cl";

    // provider names
    public static final String PROVIDER_BAIDU = "baidu";
    public static final String PROVIDER_BING = "bing";
    public static final String PROVIDER_EBAY = "ebay";
    public static final String PROVIDER_GOOGLE = "google";
    public static final String PROVIDER_YAHOO = "yahoo";
    public static final String PROVIDER_YANDEX = "yandex";
    public static final String PROVIDER_DUCKDUCKGO = "duckduckgo";

    // provider specific
    public static final String GOOGLE_DOMAIN = "google_domain"; // e.g. "google.com", "google.co.in", etc.
}
