package com.serpapi.model;

import com.google.gson.JsonObject;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 8:54 PM
 */
@FunctionalInterface
public interface JsonObjectable {
    JsonObject getJsonObject();
}
