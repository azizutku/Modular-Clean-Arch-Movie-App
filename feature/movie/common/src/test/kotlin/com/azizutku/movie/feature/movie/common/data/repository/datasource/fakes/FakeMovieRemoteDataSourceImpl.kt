package com.azizutku.movie.feature.movie.common.data.repository.datasource.fakes

import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.core.testing.models.movieDto
import com.azizutku.movie.feature.movie.common.data.remote.dto.MovieDto
import com.azizutku.movie.feature.movie.common.data.repository.datasource.MovieRemoteDataSource

class FakeMovieRemoteDataSourceImpl : MovieRemoteDataSource {

    var isSuccessful = true

    override suspend fun getMovie(movieId: Int): Result<MovieDto> {
        return if (isSuccessful) {
            Result.success(movieDto)
        } else {
            Result.failure(NetworkException(code = 1))
        }
    }
}
