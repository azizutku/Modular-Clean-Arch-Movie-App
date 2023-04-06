package com.azizutku.movie.core.domain.watchlist.repository

import androidx.paging.PagingData
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    fun addMovie(movieId: Int): Flow<DataState<MovieWatchlistState>>
    fun removeMovie(movieId: Int): Flow<DataState<MovieWatchlistState>>
    fun getAllMovies(): Flow<PagingData<WatchlistMovie>>
    fun isMovieInWatchlist(movieId: Int): Flow<DataState<MovieWatchlistState>>
}
