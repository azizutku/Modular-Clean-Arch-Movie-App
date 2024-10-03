package com.azizutku.feature.movie.common.data.repository.datasource.fakes

import com.azizutku.feature.movie.common.data.repository.datasource.MovieCacheDataSource
import com.azizutku.feature.movie.common.domain.model.Movie

class FakeMovieCacheDataSourceImpl : MovieCacheDataSource {

    private val moviesMap = hashMapOf<Int, Movie>()

    fun initWithInitialList(initialList: List<Movie>) {
        initialList.forEach { movie ->
            saveMovieToCache(movie)
        }
    }

    override fun getMovieFromCache(movieId: Int): Movie? = moviesMap[movieId]

    override fun saveMovieToCache(movie: Movie) {
        moviesMap[movie.id] = movie
    }
}
