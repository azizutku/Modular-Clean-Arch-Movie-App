package com.azizutku.movie.core.testing.fakes.watchlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.WatchlistEntity
import com.azizutku.movie.feature.watchlist.data.repository.datasource.WatchlistLocalDataSource

class FakeWatchlistLocalDataSourceImpl : WatchlistLocalDataSource {

    val watchlistMoviesMap = hashMapOf<Int, WatchlistEntity>()

    override fun getAllMoviesInWatchlist(): PagingSource<Int, MovieEntity> = FakePagingSource()

    override suspend fun addToWatchlist(entity: WatchlistEntity) {
        watchlistMoviesMap[entity.movieId] = entity
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        watchlistMoviesMap.remove(movieId)
    }

    override suspend fun isMovieInWatchlist(movieId: Int): Boolean = watchlistMoviesMap.contains(movieId)
}

class FakePagingSource : PagingSource<Int, MovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> = LoadResult.Error(Exception())
}
