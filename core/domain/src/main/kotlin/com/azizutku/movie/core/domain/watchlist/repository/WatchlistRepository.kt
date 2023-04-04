package com.azizutku.movie.core.domain.watchlist.repository

import androidx.paging.PagingData
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    suspend fun addMovie(movieId: Int): Flow<DataState<MovieWatchlistState>>
    suspend fun removeMovie(movieId: Int): Flow<DataState<MovieWatchlistState>>
    suspend fun getAllMovies(): Flow<PagingData<WatchlistMovie>>
    suspend fun isMovieInWatchlist(movieId: Int): Flow<DataState<MovieWatchlistState>>
}
