package com.azizutku.movie.features.trending.domain.model

import com.azizutku.movie.common.util.Mapper
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

const val FORMAT_DATE_MOVIE_RELEASE_PARSER = "yyyy-MM-dd"
const val BASE_URL_IMAGE_MOVIE_POSTER = "https://image.tmdb.org/t/p/w500"
private const val FORMAT_DATE_TRENDING_MOVIE_RELEASE_FORMATTER = "dd MMM yyyy"

class TrendingMoviesLocalMapper @Inject constructor() : Mapper<TrendingMovieEntity, TrendingMovie> {
    override suspend fun map(from: TrendingMovieEntity): TrendingMovie {
        val parser = SimpleDateFormat(FORMAT_DATE_MOVIE_RELEASE_PARSER, Locale.ENGLISH)
        val formatter = SimpleDateFormat(FORMAT_DATE_TRENDING_MOVIE_RELEASE_FORMATTER, Locale.ENGLISH)
        return TrendingMovie(
            id = from.id,
            description = from.description.orEmpty(),
            posterUrl = BASE_URL_IMAGE_MOVIE_POSTER + from.posterPath,
            releaseDate = runCatching {
                parser.parse(from.releaseDate.orEmpty())
            }.getOrNull()?.let { releaseDate ->
                formatter.format(releaseDate)
            }.orEmpty(),
            title = from.title.orEmpty(),
            rating = from.voteAverage?.let { voteAverage ->
                "TMDB $voteAverage"
            }.orEmpty(),
        )
    }
}
