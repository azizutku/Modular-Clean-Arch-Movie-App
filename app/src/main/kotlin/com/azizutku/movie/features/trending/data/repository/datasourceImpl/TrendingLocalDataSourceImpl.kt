package com.azizutku.movie.features.trending.data.repository.datasourceImpl

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.azizutku.movie.features.trending.data.local.TrendingMovieRemoteKeysDao
import com.azizutku.movie.features.trending.data.local.TrendingMoviesDao
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingLocalDataSource
import javax.inject.Inject

class TrendingLocalDataSourceImpl @Inject constructor(
    private val trendingMoviesDao: TrendingMoviesDao,
    private val trendingMovieRemoteKeysDao: TrendingMovieRemoteKeysDao,
) : TrendingLocalDataSource {

    override fun getPagingSourceFromDb(): PagingSource<Int, TrendingMovieEntity> =
        trendingMoviesDao.getPagingSource()

    override suspend fun insertAllMoviesToDb(list: List<TrendingMovieEntity>) {
        trendingMoviesDao.insertAll(list)
    }

    override suspend fun clearAllMoviesFromDb() {
        trendingMoviesDao.clearAll()
    }

    override suspend fun refreshDataForPaging(
        loadType: LoadType,
        page: Int,
        movies: List<TrendingMovieEntity>,
    ) {
        trendingMoviesDao.deleteAndInsertTransactionForPaging(loadType, page, movies, trendingMovieRemoteKeysDao)
    }

    override suspend fun insertAllRemoteKeysToDb(list: List<TrendingMovieRemoteKeyEntity>) {
        trendingMovieRemoteKeysDao.insertAll(list)
    }

    override suspend fun getRemoteKeyFromDb(movieId: Int): TrendingMovieRemoteKeyEntity? =
        trendingMovieRemoteKeysDao.getRemoteKey(movieId)

    override suspend fun clearAllRemoteKeysFromDb() {
        trendingMovieRemoteKeysDao.clearAll()
    }
}
