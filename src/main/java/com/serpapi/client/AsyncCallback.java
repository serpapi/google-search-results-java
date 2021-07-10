package com.serpapi.client;

/**
 * An asynchronous callback interface.
 *
 * @param <T> the type of the response.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/14/21
 * Time: 7:14 PM
 */
public interface AsyncCallback<T> {
    void completed(T response);

    void failed(Throwable throwable);
}
