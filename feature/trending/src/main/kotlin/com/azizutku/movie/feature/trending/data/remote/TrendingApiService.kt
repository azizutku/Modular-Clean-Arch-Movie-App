package com.azizutku.movie.feature.trending.data.remote

import com.azizutku.movie.feature.trending.data.remote.dto.TrendingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApiService {

    @GET("/3/trending/movie/day")
    suspend fun getTrendingMovies(@Query("page") page: Int): Result<TrendingDto>
}
