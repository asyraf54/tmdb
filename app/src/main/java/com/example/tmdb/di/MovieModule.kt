package com.example.tmdb.di

import com.example.tmdb.data.repository.MovieRepositoryImpl
import com.example.tmdb.data.source.MovieSource
import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.usecase.MovieUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    fun provideMovieSource(retrofit: Retrofit): MovieSource = retrofit.create(MovieSource::class.java)

    @Provides
    fun provideMovieRepository(movieSource: MovieSource): MovieRepository = MovieRepositoryImpl(movieSource)

    @Provides
    fun provideMovieUsecase(repository: MovieRepository): MovieUsecase = MovieUsecase(repository)
}