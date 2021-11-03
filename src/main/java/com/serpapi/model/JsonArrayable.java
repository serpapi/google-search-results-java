package com.serpapi.model;

import com.google.gson.JsonArray;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 8:56 PM
 */
@FunctionalInterface
public interface JsonArrayable {
    JsonArray getJsonArray();
}
