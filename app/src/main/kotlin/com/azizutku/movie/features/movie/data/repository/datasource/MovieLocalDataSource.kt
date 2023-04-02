package com.azizutku.movie.features.movie.data.repository.datasource

import com.azizutku.movie.core.database.model.MovieEntity

interface MovieLocalDataSource {

    suspend fun getMovie(movieId: Int): MovieEntity?
    suspend fun insertMovieToDb(entity: MovieEntity)
    suspend fun clearAllMoviesFromDb()
}
