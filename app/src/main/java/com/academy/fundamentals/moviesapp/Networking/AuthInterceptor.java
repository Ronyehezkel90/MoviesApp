package com.academy.fundamentals.moviesapp.Networking;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.academy.fundamentals.moviesapp.Constants.apiKey;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String originalUrl = originalRequest.url().url().toString();
        String newUrl = originalUrl.concat(String.format("?api_key=%s", apiKey));
        Request.Builder requestBuilder = originalRequest.newBuilder().url(newUrl);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}