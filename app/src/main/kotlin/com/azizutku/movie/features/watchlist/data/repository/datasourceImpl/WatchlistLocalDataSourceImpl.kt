package com.azizutku.movie.features.watchlist.data.repository.datasourceImpl

import androidx.paging.PagingSource
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.WatchlistEntity
import com.azizutku.movie.features.watchlist.data.repository.datasource.WatchlistLocalDataSource
import javax.inject.Inject

class WatchlistLocalDataSourceImpl @Inject constructor(
    private val watchlistDao: com.azizutku.movie.core.database.dao.WatchlistDao,
) : WatchlistLocalDataSource {

    override fun getAllMoviesInWatchlist(): PagingSource<Int, MovieEntity> = watchlistDao.getAllMoviesInWatchlist()

    override suspend fun addToWatchlist(entity: WatchlistEntity) {
        watchlistDao.insert(entity)
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        watchlistDao.delete(movieId)
    }

    override suspend fun isMovieInWatchlist(movieId: Int): Boolean = watchlistDao.isMovieExist(movieId)
}
