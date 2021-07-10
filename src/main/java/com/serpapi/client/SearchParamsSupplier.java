package com.serpapi.client;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/16/21
 * Time: 10:47 PM
 */
@FunctionalInterface
public interface SearchParamsSupplier extends Supplier<Map<String, String>> {
}
