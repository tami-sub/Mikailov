package com.example.moviessearch.data.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder().build()
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
                .url(url)
                .build()
        )
    }
}