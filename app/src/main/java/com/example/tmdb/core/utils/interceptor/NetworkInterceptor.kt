package com.example.tmdb.core.utils.interceptor

import com.example.tmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestWithHeaders = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
            .build()

        return chain.proceed(requestWithHeaders)
    }
}
