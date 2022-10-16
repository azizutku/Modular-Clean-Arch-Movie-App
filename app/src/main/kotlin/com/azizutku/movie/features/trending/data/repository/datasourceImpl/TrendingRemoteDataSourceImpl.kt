package com.azizutku.movie.features.trending.data.repository.datasourceImpl

import com.azizutku.movie.features.trending.data.remote.TrendingApiService
import com.azizutku.movie.features.trending.data.remote.dto.TrendingDto
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingRemoteDataSource
import javax.inject.Inject

class TrendingRemoteDataSourceImpl @Inject constructor(
    private val service: TrendingApiService,
) : TrendingRemoteDataSource {
    override suspend fun getTrendingMovies(page: Int): Result<TrendingDto> = service.getTrendingMovies(page)
}
