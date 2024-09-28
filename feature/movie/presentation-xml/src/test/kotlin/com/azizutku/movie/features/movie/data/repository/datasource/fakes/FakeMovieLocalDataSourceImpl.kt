package com.azizutku.movie.features.movie.data.repository.datasource.fakes

import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieLocalDataSource

class FakeMovieLocalDataSourceImpl : MovieLocalDataSource {

    private val moviesMap = hashMapOf<Int, MovieEntity>()

    fun initWithInitialList(initialList: List<MovieEntity>) {
        initialList.forEach { entity ->
            moviesMap[entity.id] = entity
        }
    }

    override suspend fun getMovie(movieId: Int): MovieEntity? = moviesMap[movieId]

    override suspend fun insertMovieToDb(entity: MovieEntity) {
        moviesMap[entity.id] = entity
    }

    override suspend fun clearAllMoviesFromDb() {
        moviesMap.clear()
    }
}
