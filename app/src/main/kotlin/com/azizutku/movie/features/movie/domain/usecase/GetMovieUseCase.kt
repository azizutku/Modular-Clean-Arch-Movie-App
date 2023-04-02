package com.azizutku.movie.features.movie.domain.usecase

import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.features.movie.domain.model.Movie
import com.azizutku.movie.features.movie.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): Flow<DataState<Movie>> = repository.getMovie(movieId)
}
