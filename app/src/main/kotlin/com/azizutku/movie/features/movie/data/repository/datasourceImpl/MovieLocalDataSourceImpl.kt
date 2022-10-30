package com.azizutku.movie.features.movie.data.repository.datasourceImpl

import com.azizutku.movie.features.movie.data.local.MoviesDao
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.movie.data.repository.datasource.MovieLocalDataSource
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
) : MovieLocalDataSource {

    override suspend fun getMovie(movieId: Int): MovieEntity? = moviesDao.getMovie(movieId)

    override suspend fun insertMovieToDb(entity: MovieEntity) {
        moviesDao.insert(entity)
    }

    override suspend fun clearAllMoviesFromDb() {
        moviesDao.clearAll()
    }
}
