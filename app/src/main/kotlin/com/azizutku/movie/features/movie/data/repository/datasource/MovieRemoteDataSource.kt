package com.azizutku.movie.features.movie.data.repository.datasource

import com.azizutku.movie.features.movie.data.remote.dto.MovieDto

interface MovieRemoteDataSource {

    suspend fun getMovie(movieId: Int): Result<MovieDto>
}
