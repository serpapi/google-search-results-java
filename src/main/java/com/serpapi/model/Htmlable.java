package com.serpapi.model;

/**
 * An HTML response from a server.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/13/21
 * Time: 8:53 PM
 */
@FunctionalInterface
public interface Htmlable {
    String getHtml();
}
