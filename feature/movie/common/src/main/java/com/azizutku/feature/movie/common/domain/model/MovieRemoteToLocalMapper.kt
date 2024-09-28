package com.azizutku.feature.movie.common.domain.model

import com.azizutku.feature.movie.common.data.remote.dto.MovieDto
import com.azizutku.movie.core.common.util.Mapper
import com.azizutku.movie.core.database.model.MovieEntity
import javax.inject.Inject

class MovieRemoteToLocalMapper @Inject constructor() : Mapper<MovieDto, MovieEntity> {

    override suspend fun map(from: MovieDto): MovieEntity = MovieEntity(
        id = from.id,
        description = from.description,
        tagline = from.tagline,
        posterPath = from.posterPath,
        releaseDate = from.releaseDate,
        title = from.title,
        voteAverage = from.voteAverage,
        genre = from.genres?.firstOrNull()?.name
    )
}
