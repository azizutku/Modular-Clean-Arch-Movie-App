package com.azizutku.movie.feature.trending.di

import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.core.database.dao.TrendingMovieRemoteKeysDao
import com.azizutku.movie.core.database.dao.TrendingMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class TrendingDatabaseModule {
    @Singleton
    @Provides
    fun provideTrendingMoviesDao(database: MainDatabase): TrendingMoviesDao = database.trendingMoviesDao()

    @Singleton
    @Provides
    fun provideTrendingMovieRemoteKeysDao(database: MainDatabase): TrendingMovieRemoteKeysDao =
        database.trendingMovieRemoteKeysDao()
}
