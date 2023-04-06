package com.azizutku.movie.feature.trending.data.repository.datasource

import com.azizutku.movie.feature.trending.data.remote.dto.TrendingDto

interface TrendingRemoteDataSource {
    suspend fun getTrendingMovies(page: Int): Result<TrendingDto>
}
