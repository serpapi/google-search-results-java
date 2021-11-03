package com.serpapi.model.responses;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.serpapi.model.JsonArrayable;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 9:14 PM
 */
public class LocationsResponse extends SerpApiResponse implements JsonArrayable {

    private LocationsResponse(JsonArray locations) {
        super(null, locations);
    }

    public static LocationsResponse from(String jsonResponse) {
        JsonElement element = GSON.fromJson(jsonResponse, JsonElement.class);
        return new LocationsResponse(element.getAsJsonArray());
    }

    @Override
    public JsonArray getJsonArray() {
        return (JsonArray) jsonElement;
    }
}
