package com.azizutku.movie.features.watchlist.data.repository.datasource

import androidx.paging.PagingSource
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.watchlist.data.local.entity.WatchlistEntity

interface WatchlistLocalDataSource {

    fun getAllMoviesInWatchlist(): PagingSource<Int, MovieEntity>
    suspend fun addToWatchlist(entity: WatchlistEntity)
    suspend fun removeFromWatchlist(movieId: Int)
    suspend fun isMovieInWatchlist(movieId: Int): Boolean
}
