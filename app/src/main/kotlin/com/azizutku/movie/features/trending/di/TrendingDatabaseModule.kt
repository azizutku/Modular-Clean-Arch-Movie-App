package com.azizutku.movie.features.trending.di

import com.azizutku.movie.common.data.MainDatabase
import com.azizutku.movie.features.trending.data.local.TrendingMovieRemoteKeysDao
import com.azizutku.movie.features.trending.data.local.TrendingMoviesDao
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
