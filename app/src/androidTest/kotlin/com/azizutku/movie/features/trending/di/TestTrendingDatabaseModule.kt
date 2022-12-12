package com.azizutku.movie.features.trending.di

import com.azizutku.movie.common.data.MainDatabase
import com.azizutku.movie.features.trending.data.local.TrendingMovieRemoteKeysDao
import com.azizutku.movie.features.trending.data.local.TrendingMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TrendingDatabaseModule::class],
)
object TestTrendingDatabaseModule {

    @Singleton
    @Provides
    fun provideTrendingMoviesDao(database: MainDatabase): TrendingMoviesDao = database.trendingMoviesDao()

    @Singleton
    @Provides
    fun provideTrendingMovieRemoteKeysDao(database: MainDatabase): TrendingMovieRemoteKeysDao =
        database.trendingMovieRemoteKeysDao()
}
