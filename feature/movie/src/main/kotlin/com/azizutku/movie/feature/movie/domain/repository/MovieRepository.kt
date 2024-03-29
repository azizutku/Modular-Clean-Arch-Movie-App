package com.azizutku.movie.feature.movie.domain.repository

import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(movieId: Int): Flow<DataState<Movie>>
}
