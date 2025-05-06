package com.example.tmdb.di

import com.example.tmdb.data.repository.MovieRepositoryImpl
import com.example.tmdb.data.source.MovieLocalSource
import com.example.tmdb.data.source.MovieRemoteSource
import com.example.tmdb.domain.repository.MovieRepository
import com.example.tmdb.domain.usecase.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    fun provideMovieSource(retrofit: Retrofit): MovieRemoteSource =
        retrofit.create(MovieRemoteSource::class.java)

    @Provides
    fun provideMovieRepository(
        movieRemoteSource: MovieRemoteSource,
        movieLocalSource: MovieLocalSource
    ): MovieRepository = MovieRepositoryImpl(movieRemoteSource, movieLocalSource)

    @Provides
    fun provideMovieUseCase(repository: MovieRepository): MovieUseCase = MovieUseCase(repository)
}