package com.azizutku.feature.movie.common.data.repository.datasource

import com.azizutku.feature.movie.common.domain.model.Movie

interface MovieCacheDataSource {

    fun getMovieFromCache(movieId: Int): Movie?
    fun saveMovieToCache(movie: Movie)
}
