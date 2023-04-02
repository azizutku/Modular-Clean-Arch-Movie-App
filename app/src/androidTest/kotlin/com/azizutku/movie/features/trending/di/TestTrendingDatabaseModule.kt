package com.azizutku.movie.features.trending.di

import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.core.database.dao.TrendingMovieRemoteKeysDao
import com.azizutku.movie.core.database.dao.TrendingMoviesDao
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
