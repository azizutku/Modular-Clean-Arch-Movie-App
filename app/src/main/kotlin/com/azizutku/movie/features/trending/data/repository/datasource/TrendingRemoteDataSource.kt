package com.azizutku.movie.features.trending.data.repository.datasource

import com.azizutku.movie.features.trending.data.remote.dto.TrendingDto

interface TrendingRemoteDataSource {
    suspend fun getTrendingMovies(page: Int): Result<TrendingDto>
}
