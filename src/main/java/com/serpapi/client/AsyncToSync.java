package com.serpapi.client;

import com.serpapi.exceptions.SerpApiException;

/**
 * A callback that can be used to convert asynchronous I/O to synchronous.
 *
 * Created by:
 * User: Anand Ganesh
 * Date: 7/14/21
 * Time: 7:46 PM
 */
public class AsyncToSync<T> implements AsyncCallback<T> {
    private T response;
    private Throwable throwable;

    private synchronized void signal(T response) {
        this.response = response;
        this.notify();
    }

    private synchronized void signal(Throwable throwable) {
        this.throwable = throwable;
        this.notify();
    }

    public synchronized T waitForCompletion() throws SerpApiException {
        while (response == null && throwable == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new SerpApiException(e);
            }
        }
        if (throwable != null) {
            throw new SerpApiException(throwable);
        }
        return response;
    }

    @Override
    public void completed(T response) {
        this.signal(response);
    }

    @Override
    public void failed(Throwable throwable) {
        this.signal(throwable);
    }
}
