package com.serpapi.model.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.serpapi.model.Htmlable;
import com.serpapi.model.JsonObjectable;

import java.util.Optional;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 8:52 PM
 */
public class SearchResponse extends SerpApiResponse implements Htmlable, JsonObjectable {

    private SearchResponse(String html, JsonObject jsonObject) {
        super(html, jsonObject);
    }

    public static SearchResponse forHtml(String html) {
        return new SearchResponse(html, null);
    }

    public static SearchResponse forJson(String jsonResponse) {
        JsonElement element = GSON.fromJson(jsonResponse, JsonElement.class);
        return new SearchResponse(null, element.getAsJsonObject());
    }

    @Override
    public String getHtml() {
        return Optional.ofNullable(html)
                .orElseThrow(() -> new IllegalStateException("getHtml() invoked on a JSON search response"));
    }

    @Override
    public JsonObject getJsonObject() {
        return Optional.ofNullable((JsonObject) jsonElement)
                .orElseThrow(() -> new IllegalStateException("getJsonObject() invoked on an HTML search response"));
    }
}
