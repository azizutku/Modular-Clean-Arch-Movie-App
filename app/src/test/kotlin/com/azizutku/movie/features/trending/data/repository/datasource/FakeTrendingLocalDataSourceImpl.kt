package com.azizutku.movie.features.trending.data.repository.datasource

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.features.trending.data.repository.mediator.LAST_PAGE
import com.azizutku.movie.features.trending.data.repository.mediator.TMDB_FIRST_PAGE_INDEX

class FakeTrendingLocalDataSourceImpl : TrendingLocalDataSource {

    private val moviesMap = hashMapOf<Int, TrendingMovieEntity>()
    private val movieRemoteKeysMap = hashMapOf<Int, TrendingMovieRemoteKeyEntity>()

    override fun getPagingSourceFromDb(): PagingSource<Int, TrendingMovieEntity> = FakePagingSource()

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
                nextKey = nextKey
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

    private class FakePagingSource : PagingSource<Int, TrendingMovieEntity>() {

        override fun getRefreshKey(state: PagingState<Int, TrendingMovieEntity>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingMovieEntity> {
            return LoadResult.Error(RuntimeException())
        }
    }
}
