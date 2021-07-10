package com.serpapi.model.responses;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * A response to a call to SerpApi.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 9:49 PM
 */
public abstract class SerpApiResponse {
    protected static final Gson GSON = new Gson(); // a singleton static object; is MT-safe

    protected final String html;
    protected final JsonElement jsonElement;

    protected SerpApiResponse(String html, JsonElement jsonElement) {
        this.html = html;
        this.jsonElement = jsonElement;
    }
}
