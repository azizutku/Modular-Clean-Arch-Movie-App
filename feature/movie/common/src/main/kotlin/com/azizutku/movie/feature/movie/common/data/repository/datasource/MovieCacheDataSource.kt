package com.azizutku.movie.feature.movie.common.data.repository.datasource

import com.azizutku.movie.feature.movie.common.domain.model.Movie

interface MovieCacheDataSource {

    fun getMovieFromCache(movieId: Int): Movie?
    fun saveMovieToCache(movie: Movie)
}
