package com.nytimes.populararticles.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This okhttp interceptor is responsible for adding the common query parameters and headers
 * for every service calls
 */
@Singleton
public class RequestInterceptor implements Interceptor {

    @Inject
    public RequestInterceptor() {
        // Intentionally blank
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api-key", ApiConstants.API_KEY)
                .build();

        Request request = originalRequest.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}