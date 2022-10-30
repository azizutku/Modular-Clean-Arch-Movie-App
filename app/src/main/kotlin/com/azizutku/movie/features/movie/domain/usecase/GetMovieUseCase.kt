package com.azizutku.movie.features.movie.domain.usecase

import com.azizutku.movie.common.vo.DataState
import com.azizutku.movie.features.movie.domain.model.Movie
import com.azizutku.movie.features.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): Flow<DataState<Movie>> = repository.getMovie(movieId)
}
