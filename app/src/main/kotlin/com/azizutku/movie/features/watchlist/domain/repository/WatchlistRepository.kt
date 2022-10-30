package com.azizutku.movie.features.watchlist.domain.repository

import androidx.paging.PagingData
import com.azizutku.movie.common.vo.DataState
import com.azizutku.movie.features.watchlist.domain.model.MovieWatchlistState
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    suspend fun addMovie(movieId: Int): Flow<DataState<MovieWatchlistState>>
    suspend fun removeMovie(movieId: Int): Flow<DataState<MovieWatchlistState>>
    suspend fun getAllMovies(): Flow<PagingData<WatchlistMovie>>
    suspend fun isMovieInWatchlist(movieId: Int): Flow<DataState<MovieWatchlistState>>
}
