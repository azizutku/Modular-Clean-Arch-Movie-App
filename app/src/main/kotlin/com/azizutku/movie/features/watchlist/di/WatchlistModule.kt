package com.azizutku.movie.features.watchlist.di

import com.azizutku.movie.common.util.Mapper
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.watchlist.data.repository.WatchlistRepositoryImpl
import com.azizutku.movie.features.watchlist.data.repository.datasource.WatchlistLocalDataSource
import com.azizutku.movie.features.watchlist.data.repository.datasourceImpl.WatchlistLocalDataSourceImpl
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovieLocalMapper
import com.azizutku.movie.features.watchlist.domain.repository.WatchlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class WatchlistModule {

    @ViewModelScoped
    @Binds
    abstract fun bindWatchlistRepository(repository: WatchlistRepositoryImpl): WatchlistRepository

    @ViewModelScoped
    @Binds
    abstract fun bindLocalDataSource(impl: WatchlistLocalDataSourceImpl): WatchlistLocalDataSource

    @ViewModelScoped
    @Binds
    abstract fun bindLocalMapper(impl: WatchlistMovieLocalMapper): Mapper<MovieEntity, WatchlistMovie>
}
