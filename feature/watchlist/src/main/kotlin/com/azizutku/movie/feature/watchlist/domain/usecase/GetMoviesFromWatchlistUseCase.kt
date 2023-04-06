package com.azizutku.movie.feature.watchlist.domain.usecase

import androidx.paging.PagingData
import com.azizutku.movie.core.domain.watchlist.repository.WatchlistRepository
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMoviesFromWatchlistUseCase @Inject constructor(private val repository: WatchlistRepository) {

    suspend operator fun invoke(): Flow<PagingData<WatchlistMovie>> = repository.getAllMovies()
}
