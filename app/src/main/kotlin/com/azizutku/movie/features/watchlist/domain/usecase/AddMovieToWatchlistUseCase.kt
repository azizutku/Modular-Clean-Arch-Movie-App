package com.azizutku.movie.features.watchlist.domain.usecase

import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.features.watchlist.domain.model.MovieWatchlistState
import com.azizutku.movie.features.watchlist.domain.repository.WatchlistRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AddMovieToWatchlistUseCase @Inject constructor(private val repository: WatchlistRepository) {

    suspend operator fun invoke(movieId: Int): Flow<DataState<MovieWatchlistState>> = repository.addMovie(movieId)
}
