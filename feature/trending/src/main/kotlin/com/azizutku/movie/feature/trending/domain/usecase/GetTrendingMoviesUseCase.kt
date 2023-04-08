package com.azizutku.movie.feature.trending.domain.usecase

import androidx.paging.PagingData
import com.azizutku.movie.feature.trending.domain.model.TrendingMovie
import com.azizutku.movie.feature.trending.domain.repository.TrendingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTrendingMoviesUseCase @Inject constructor(private val repository: TrendingRepository) {
    operator fun invoke(): Flow<PagingData<TrendingMovie>> = repository.getTrendingMovies()
}
