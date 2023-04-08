package com.azizutku.movie.core.domain.watchlist.usecase

import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.domain.watchlist.repository.WatchlistRepository
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CheckMovieInWatchlistUseCase @Inject constructor(private val repository: WatchlistRepository) {

    operator fun invoke(movieId: Int): Flow<DataState<MovieWatchlistState>> =
        repository.isMovieInWatchlist(movieId)
}
