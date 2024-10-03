package com.azizutku.movie.feature.trending.common.domain.repository

import androidx.paging.PagingData
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovie
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {

    fun getTrendingMovies(): Flow<PagingData<TrendingMovie>>
}
