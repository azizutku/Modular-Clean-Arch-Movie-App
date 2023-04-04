package com.azizutku.movie.feature.watchlist.domain.model

import com.azizutku.movie.core.common.util.BASE_URL_IMAGE_MOVIE_POSTER
import com.azizutku.movie.core.common.util.Mapper
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import javax.inject.Inject

class WatchlistMovieLocalMapper @Inject constructor() : Mapper<MovieEntity, WatchlistMovie> {

    override suspend fun map(from: MovieEntity): WatchlistMovie = WatchlistMovie(
        id = from.id,
        posterUrl = BASE_URL_IMAGE_MOVIE_POSTER + from.posterPath,
    )
}
