package com.azizutku.movie.features.trending.data.repository.datasource

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.azizutku.movie.core.database.model.TrendingMovieEntity
import com.azizutku.movie.core.database.model.TrendingMovieRemoteKeyEntity

interface TrendingLocalDataSource {
    fun getPagingSourceFromDb(): PagingSource<Int, TrendingMovieEntity>
    suspend fun insertAllMoviesToDb(list: List<TrendingMovieEntity>)
    suspend fun clearAllMoviesFromDb()
    suspend fun refreshDataForPaging(loadType: LoadType, page: Int, movies: List<TrendingMovieEntity>)
    suspend fun insertAllRemoteKeysToDb(list: List<TrendingMovieRemoteKeyEntity>)
    suspend fun getRemoteKeyFromDb(movieId: Int): TrendingMovieRemoteKeyEntity?
    suspend fun clearAllRemoteKeysFromDb()
}
