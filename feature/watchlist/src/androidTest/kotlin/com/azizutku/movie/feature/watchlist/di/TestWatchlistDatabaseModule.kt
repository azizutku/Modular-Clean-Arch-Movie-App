package com.azizutku.movie.feature.watchlist.di

import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.core.database.dao.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [WatchlistDatabaseModule::class],
)
object TestWatchlistDatabaseModule {

    @Singleton
    @Provides
    fun provideWatchlistDao(database: MainDatabase): WatchlistDao =
        database.watchlistDao()
}
