package com.azizutku.movie.feature.trending.data.repository.datasource.fakes

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azizutku.movie.core.database.dao.LAST_PAGE
import com.azizutku.movie.core.database.dao.TMDB_FIRST_PAGE_INDEX
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.TrendingMovieEntity
import com.azizutku.movie.core.database.model.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.feature.trending.data.repository.datasource.TrendingLocalDataSource
import com.azizutku.movie.feature.trending.domain.model.TrendingMovie

class FakeTrendingLocalDataSourceImpl : TrendingLocalDataSource {

    var trendingMovieEntities = emptyList<TrendingMovieEntity>()
    private val moviesMap = hashMapOf<Int, TrendingMovieEntity>()
    private val movieRemoteKeysMap = hashMapOf<Int, TrendingMovieRemoteKeyEntity>()

    override fun getPagingSourceFromDb(): PagingSource<Int, TrendingMovieEntity> =
        FakePagingSource(trendingMovieEntities)

    override suspend fun insertAllMoviesToDb(list: List<TrendingMovieEntity>) {
        list.forEach { movie ->
            moviesMap[movie.id] = movie
        }
    }

    override suspend fun clearAllMoviesFromDb() {
        moviesMap.clear()
    }

    override suspend fun refreshDataForPaging(
        loadType: LoadType,
        page: Int,
        movies: List<TrendingMovieEntity>,
    ) {
        moviesMap.clear()
        movieRemoteKeysMap.clear()
        val prevKey = page.minus(1).takeUnless { page == TMDB_FIRST_PAGE_INDEX }
        val nextKey = page.plus(1).takeUnless { page == LAST_PAGE }
        movies.forEach { movie ->
            moviesMap[movie.id] = movie
            movieRemoteKeysMap[movie.id] = TrendingMovieRemoteKeyEntity(
                id = movie.id,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        }
    }

    override suspend fun insertAllRemoteKeysToDb(list: List<TrendingMovieRemoteKeyEntity>) {
        list.forEach { movieRemoteKey ->
            movieRemoteKeysMap[movieRemoteKey.id] = movieRemoteKey
        }
    }

    override suspend fun getRemoteKeyFromDb(movieId: Int): TrendingMovieRemoteKeyEntity? =
        movieRemoteKeysMap[movieId]

    override suspend fun clearAllRemoteKeysFromDb() {
        movieRemoteKeysMap.clear()
    }

    private class FakePagingSource(
        private val trendingMovieEntities: List<TrendingMovieEntity>,
    ) : PagingSource<Int, TrendingMovieEntity>() {

        override fun getRefreshKey(state: PagingState<Int, TrendingMovieEntity>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingMovieEntity> {
            return LoadResult.Page(
                data = trendingMovieEntities,
                prevKey = null,
                nextKey = null,
            )
        }
    }
}
