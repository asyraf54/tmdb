package com.example.tmdb.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.tmdb.BuildConfig
import com.example.tmdb.core.utils.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}