package com.azizutku.movie.features.watchlist.domain.usecase

import androidx.paging.PagingData
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie
import com.azizutku.movie.features.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesFromWatchlistUseCase @Inject constructor(private val repository: WatchlistRepository) {

    suspend operator fun invoke(): Flow<PagingData<WatchlistMovie>> = repository.getAllMovies()
}
