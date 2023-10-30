package com.azizutku.movie.feature.watchlist.data.repository.datasourceImpl

import androidx.paging.PagingSource
import com.azizutku.movie.core.database.dao.WatchlistDao
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.WatchlistEntity
import com.azizutku.movie.feature.watchlist.data.repository.datasource.WatchlistLocalDataSource
import javax.inject.Inject

class WatchlistLocalDataSourceImpl @Inject constructor(
    private val watchlistDao: WatchlistDao,
) : WatchlistLocalDataSource {

    override fun getAllMoviesInWatchlist(): PagingSource<Int, MovieEntity> = watchlistDao.getAllMoviesInWatchlist()

    override suspend fun addToWatchlist(entity: WatchlistEntity) {
        watchlistDao.upsert(entity)
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        watchlistDao.delete(movieId)
    }

    override suspend fun isMovieInWatchlist(movieId: Int): Boolean = watchlistDao.isMovieExist(movieId)
}
