package com.azizutku.movie.features.watchlist.di

import com.azizutku.movie.common.data.MainDatabase
import com.azizutku.movie.features.watchlist.data.local.WatchlistDao
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
    fun provideWatchlistDao(database: MainDatabase): WatchlistDao = database.watchlistDao()
}
