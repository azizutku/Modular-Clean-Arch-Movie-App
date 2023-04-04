package com.azizutku.movie.feature.trending.domain.repository

import androidx.paging.PagingData
import com.azizutku.movie.feature.trending.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {

    suspend fun getTrendingMovies(): Flow<PagingData<TrendingMovie>>
}
