package com.azizutku.movie.features.watchlist.domain.model

import com.azizutku.movie.common.util.Mapper
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.trending.domain.model.BASE_URL_IMAGE_MOVIE_POSTER
import javax.inject.Inject

class WatchlistMovieLocalMapper @Inject constructor() : Mapper<MovieEntity, WatchlistMovie> {

    override suspend fun map(from: MovieEntity): WatchlistMovie = WatchlistMovie(
        id = from.id,
        posterUrl = BASE_URL_IMAGE_MOVIE_POSTER + from.posterPath,
    )
}
