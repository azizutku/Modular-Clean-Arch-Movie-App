package com.azizutku.movie.features.movie.domain.repository

import com.azizutku.movie.common.vo.DataState
import com.azizutku.movie.features.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(movieId: Int): Flow<DataState<Movie>>
}
