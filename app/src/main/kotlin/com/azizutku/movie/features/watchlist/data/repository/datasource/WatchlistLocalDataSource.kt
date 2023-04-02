package com.azizutku.movie.features.watchlist.data.repository.datasource

import androidx.paging.PagingSource
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.WatchlistEntity

interface WatchlistLocalDataSource {

    fun getAllMoviesInWatchlist(): PagingSource<Int, MovieEntity>
    suspend fun addToWatchlist(entity: WatchlistEntity)
    suspend fun removeFromWatchlist(movieId: Int)
    suspend fun isMovieInWatchlist(movieId: Int): Boolean
}
