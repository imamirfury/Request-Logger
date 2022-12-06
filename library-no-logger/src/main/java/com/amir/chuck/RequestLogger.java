package com.amir.chuck;


import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class RequestLogger implements Interceptor {

    public enum Period {
        ONE_HOUR,
        ONE_DAY,
        ONE_WEEK,
        FOREVER
    }

    public RequestLogger(Context context) {

    }

    public RequestLogger showNotification(boolean show) {
        return this;
    }

    public RequestLogger maxContentLength(long max) {
        return this;
    }

    public RequestLogger retainDataFor(Period period) {
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }
}
