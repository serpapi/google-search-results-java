package com.serpapi.model.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.serpapi.model.JsonObjectable;

/**
 * Response received from a call to the /account ReST endpoint
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 10:03 PM
 */
public class AccountResponse extends SerpApiResponse implements JsonObjectable {
    private AccountResponse(JsonObject accountInfo) {
        super(null, accountInfo);
    }

    public static AccountResponse from(String jsonResponse) {
        JsonElement element = GSON.fromJson(jsonResponse, JsonElement.class);
        return new AccountResponse(element.getAsJsonObject());
    }

    @Override
    public JsonObject getJsonObject() {
        return (JsonObject) jsonElement;
    }
}
