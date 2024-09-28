package com.azizutku.feature.movie.common.data.repository.datasourceImpl

import android.util.SparseArray
import com.azizutku.feature.movie.common.data.repository.datasource.MovieCacheDataSource
import com.azizutku.feature.movie.common.domain.model.Movie
import javax.inject.Inject

class MovieCacheDataSourceImpl @Inject constructor() : MovieCacheDataSource {

    private val moviesSparseArray = SparseArray<Movie>()

    override fun getMovieFromCache(movieId: Int): Movie? = moviesSparseArray.get(movieId)

    override fun saveMovieToCache(movie: Movie) {
        moviesSparseArray.put(movie.id, movie)
    }
}
