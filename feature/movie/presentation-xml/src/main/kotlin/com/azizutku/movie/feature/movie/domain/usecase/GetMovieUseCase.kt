package com.azizutku.movie.feature.movie.domain.usecase

import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.feature.movie.domain.model.Movie
import com.azizutku.movie.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(movieId: Int): Flow<DataState<Movie>> = repository.getMovie(movieId)
}
