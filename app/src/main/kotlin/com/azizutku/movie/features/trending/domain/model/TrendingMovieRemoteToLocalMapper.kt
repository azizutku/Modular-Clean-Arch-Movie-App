package com.azizutku.movie.features.trending.domain.model

import com.azizutku.movie.common.util.Mapper
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.remote.dto.TrendingMovieDto
import javax.inject.Inject

class TrendingMovieRemoteToLocalMapper @Inject constructor() : Mapper<TrendingMovieDto, TrendingMovieEntity> {
    override suspend fun map(from: TrendingMovieDto): TrendingMovieEntity = TrendingMovieEntity(
        id = from.id,
        description = from.description,
        posterPath = from.posterPath,
        releaseDate = from.releaseDate,
        title = from.title,
        voteAverage = from.voteAverage,
    )
}
