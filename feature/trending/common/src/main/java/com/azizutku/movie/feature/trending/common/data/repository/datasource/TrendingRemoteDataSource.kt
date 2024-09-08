package com.azizutku.movie.feature.trending.common.data.repository.datasource

import com.azizutku.movie.feature.trending.common.data.remote.dto.TrendingDto

interface TrendingRemoteDataSource {
    suspend fun getTrendingMovies(page: Int): Result<TrendingDto>
}
