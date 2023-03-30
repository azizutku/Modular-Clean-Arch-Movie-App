package com.azizutku.movie.features.movie.data.repository.datasource.fakes

import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.feature.movie.data.remote.dto.MovieDto
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieRemoteDataSource
import com.azizutku.movie.core.testing.models.movieDto

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
