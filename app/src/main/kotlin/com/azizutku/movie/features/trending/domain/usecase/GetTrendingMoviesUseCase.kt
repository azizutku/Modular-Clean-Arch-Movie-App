package com.azizutku.movie.features.trending.domain.usecase

import androidx.paging.PagingData
import com.azizutku.movie.features.trending.domain.model.TrendingMovie
import com.azizutku.movie.features.trending.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(private val repository: TrendingRepository) {
    suspend operator fun invoke(): Flow<PagingData<TrendingMovie>> = repository.getTrendingMovies()
}
