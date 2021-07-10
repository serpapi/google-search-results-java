package com.serpapi.model.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.serpapi.exceptions.SerpApiException;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 10:43 PM
 */
public class ErrorResponse extends SerpApiResponse { // extends SerpApiResponse to access 'GSON'; yeah, lame.
    private ErrorResponse(String html, JsonElement jsonElement) {
        super(html, jsonElement);
        throw new IllegalStateException("who instantiated this?");
    }

    public static SerpApiException from(String errorResponseBody, int httpStatus, String prefix) {
        try {
            JsonObject jsonObject = GSON.fromJson(errorResponseBody, JsonObject.class);
            String errorMessage = jsonObject.get("error").getAsString();
            return new SerpApiException(prefix + ": " + errorMessage + ", httpStatus=" + httpStatus);
        } catch (Throwable e) {
            return new SerpApiException(prefix + ": invalid response format: " + errorResponseBody
                    + ", httpStatus=" + httpStatus);
        }
    }
}
