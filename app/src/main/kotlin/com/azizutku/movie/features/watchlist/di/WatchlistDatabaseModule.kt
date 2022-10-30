package com.azizutku.movie.features.watchlist.di

import com.azizutku.movie.common.data.MainDatabase
import com.azizutku.movie.features.watchlist.data.local.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WatchlistDatabaseModule {

    @Singleton
    @Provides
    fun provideWatchlistDao(database: MainDatabase): WatchlistDao = database.watchlistDao()
}
