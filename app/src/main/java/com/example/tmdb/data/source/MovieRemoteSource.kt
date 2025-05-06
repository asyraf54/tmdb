package com.example.tmdb.data.source

import com.example.tmdb.data.model.MovieDetailResponse
import com.example.tmdb.data.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRemoteSource {
    @GET("movie/{type}")
    suspend fun getMovies(@Path("type") type: String, @Query("page") page: Int): MovieListResponse
    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): MovieDetailResponse
}
