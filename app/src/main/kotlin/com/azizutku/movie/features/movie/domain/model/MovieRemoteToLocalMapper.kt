package com.azizutku.movie.features.movie.domain.model

import com.azizutku.movie.common.util.Mapper
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.movie.data.remote.dto.MovieDto
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
