// TODO: Move this file shared folder between androidTest and test when testFixtures supports Kotlin.

package com.azizutku.movie.features.trending.repository.datasource

import com.azizutku.movie.features.trending.data.remote.dto.TrendingDto
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingRemoteDataSource
import com.azizutku.movie.models.testTrendingDto

class FakeTrendingRemoteDataSourceImpl : TrendingRemoteDataSource {

    var isSuccessful = true
    var trendingDto: TrendingDto? = null

    override suspend fun getTrendingMovies(page: Int): Result<TrendingDto> {
        return if (isSuccessful) {
            Result.success(trendingDto ?: testTrendingDto)
        } else {
            Result.failure(RuntimeException())
        }
    }
}
