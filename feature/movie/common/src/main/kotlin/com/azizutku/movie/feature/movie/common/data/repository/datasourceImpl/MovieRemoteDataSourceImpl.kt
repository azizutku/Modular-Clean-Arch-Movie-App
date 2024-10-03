package com.azizutku.movie.feature.movie.common.data.repository.datasourceImpl

import com.azizutku.movie.feature.movie.common.data.remote.MovieApiService
import com.azizutku.movie.feature.movie.common.data.remote.dto.MovieDto
import com.azizutku.movie.feature.movie.common.data.repository.datasource.MovieRemoteDataSource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val service: MovieApiService,
) : MovieRemoteDataSource {

    override suspend fun getMovie(movieId: Int): Result<MovieDto> = service.getMovie(movieId)
}
