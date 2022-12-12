package com.azizutku.movie.features.movie.data.repository.datasource

import com.azizutku.movie.common.network.NetworkException
import com.azizutku.movie.features.movie.data.remote.dto.MovieDto
import com.azizutku.movie.models.movieDto

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
