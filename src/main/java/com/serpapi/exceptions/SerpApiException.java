package com.serpapi.exceptions;

/**
 * Created by:
 * User: Anand Ganesh
 * Date: 7/11/21
 * Time: 10:17 AM
 */
public class SerpApiException extends Exception {
    public SerpApiException(Throwable t) {
        super(t);
    }

    public SerpApiException(String message) {
        super(message);
    }
}
