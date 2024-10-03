package com.azizutku.feature.movie.common.data.repository.datasource

import com.azizutku.feature.movie.common.data.remote.dto.MovieDto

interface MovieRemoteDataSource {

    suspend fun getMovie(movieId: Int): Result<MovieDto>
}
