package com.azizutku.movie.features.watchlist.domain.usecase

import com.azizutku.movie.common.vo.DataState
import com.azizutku.movie.features.watchlist.domain.model.MovieWatchlistState
import com.azizutku.movie.features.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckMovieInWatchlistUseCase @Inject constructor(private val repository: WatchlistRepository) {

    suspend operator fun invoke(movieId: Int): Flow<DataState<MovieWatchlistState>> =
        repository.isMovieInWatchlist(movieId)
}
