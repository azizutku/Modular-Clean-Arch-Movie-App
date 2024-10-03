package com.azizutku.feature.movie.common.domain.repository

import com.azizutku.feature.movie.common.domain.model.Movie
import com.azizutku.movie.core.common.vo.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(movieId: Int): Flow<DataState<Movie>>
}
