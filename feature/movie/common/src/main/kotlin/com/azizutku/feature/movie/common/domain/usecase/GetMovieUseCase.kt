package com.azizutku.feature.movie.common.domain.usecase

import com.azizutku.feature.movie.common.domain.model.Movie
import com.azizutku.feature.movie.common.domain.repository.MovieRepository
import com.azizutku.movie.core.common.vo.DataState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(movieId: Int): Flow<DataState<Movie>> = repository.getMovie(movieId)
}
