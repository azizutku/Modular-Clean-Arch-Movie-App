package com.azizutku.movie.feature.movie.data.repository.datasourceImpl

import android.util.SparseArray
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieCacheDataSource
import com.azizutku.movie.feature.movie.domain.model.Movie
import javax.inject.Inject

class MovieCacheDataSourceImpl @Inject constructor() : MovieCacheDataSource {

    private val moviesSparseArray = SparseArray<Movie>()

    override fun getMovieFromCache(movieId: Int): Movie? = moviesSparseArray.get(movieId)

    override fun saveMovieToCache(movie: Movie) {
        moviesSparseArray.put(movie.id, movie)
    }
}
