package com.azizutku.movie.features.movie.data.repository.datasourceImpl

import com.azizutku.movie.features.movie.data.remote.MovieApiService
import com.azizutku.movie.features.movie.data.remote.dto.MovieDto
import com.azizutku.movie.features.movie.data.repository.datasource.MovieRemoteDataSource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val service: MovieApiService,
) : MovieRemoteDataSource {

    override suspend fun getMovie(movieId: Int): Result<MovieDto> = service.getMovie(movieId)
}
