package com.example.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.core.utils.db.AppDatabase
import com.example.tmdb.data.source.MovieLocalSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    fun provideMovieLocalSource(db: AppDatabase): MovieLocalSource {
        return db.movieLocalSource()
    }
}