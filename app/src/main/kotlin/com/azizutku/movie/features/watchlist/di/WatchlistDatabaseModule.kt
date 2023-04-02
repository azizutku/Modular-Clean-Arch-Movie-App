package com.azizutku.movie.features.watchlist.di

import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.core.database.dao.WatchlistDao
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
