package com.azizutku.movie.feature.movie.data.repository.datasource

import com.azizutku.movie.feature.movie.domain.model.Movie

interface MovieCacheDataSource {

    fun getMovieFromCache(movieId: Int): Movie?
    fun saveMovieToCache(movie: Movie)
}
