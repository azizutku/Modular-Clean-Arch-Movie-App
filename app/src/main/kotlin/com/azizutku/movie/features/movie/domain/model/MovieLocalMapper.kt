package com.azizutku.movie.features.movie.domain.model

import com.azizutku.movie.common.util.Mapper
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.trending.domain.model.BASE_URL_IMAGE_MOVIE_POSTER
import com.azizutku.movie.features.trending.domain.model.FORMAT_DATE_MOVIE_RELEASE_PARSER
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

private const val FORMAT_DATE_MOVIE_RELEASE_FORMATTER = "yyyy"
private const val SEPARATOR_SUBTITLE = " | "
private const val CHAR_STAR = '★'

class MovieLocalMapper @Inject constructor() : Mapper<MovieEntity, Movie> {

    override suspend fun map(from: MovieEntity): Movie {
        val parser = SimpleDateFormat(FORMAT_DATE_MOVIE_RELEASE_PARSER, Locale.ENGLISH)
        val formatter = SimpleDateFormat(FORMAT_DATE_MOVIE_RELEASE_FORMATTER, Locale.ENGLISH)
        val releaseDate = parser.parse(from.releaseDate)?.let {
            formatter.format(it)
        }
        return Movie(
            id = from.id,
            description = from.description.orEmpty(),
            tagline = from.tagline.orEmpty(),
            posterUrl = BASE_URL_IMAGE_MOVIE_POSTER + from.posterPath,
            title = from.title.orEmpty(),
            subtitle = listOfNotNull(
                releaseDate,
                from.genre,
                from.voteAverage.toString().plus(CHAR_STAR)
            ).joinToString(SEPARATOR_SUBTITLE),
        )
    }
}
