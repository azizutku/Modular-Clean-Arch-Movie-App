package com.azizutku.movie.features.movie.data.repository.datasource

import com.azizutku.movie.features.movie.domain.model.Movie

interface MovieCacheDataSource {

    fun getMovieFromCache(movieId: Int): Movie?
    fun saveMovieToCache(movie: Movie)
}
