package com.azizutku.movie.features.trending.domain.repository

import androidx.paging.PagingData
import com.azizutku.movie.features.trending.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {

    suspend fun getTrendingMovies(): Flow<PagingData<TrendingMovie>>
}
