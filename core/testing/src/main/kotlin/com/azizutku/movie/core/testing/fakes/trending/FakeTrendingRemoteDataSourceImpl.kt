package com.azizutku.movie.core.testing.fakes.trending

import com.azizutku.movie.core.testing.models.testTrendingDto
import com.azizutku.movie.feature.trending.common.data.remote.dto.TrendingDto
import com.azizutku.movie.feature.trending.common.data.repository.datasource.TrendingRemoteDataSource

class FakeTrendingRemoteDataSourceImpl : TrendingRemoteDataSource {

    var isSuccessful = true
    var trendingDto: TrendingDto? = null

    override suspend fun getTrendingMovies(page: Int): Result<TrendingDto> = if (isSuccessful) {
        Result.success(trendingDto ?: testTrendingDto)
    } else {
        Result.failure(RuntimeException("Cannot access to API"))
    }
}
