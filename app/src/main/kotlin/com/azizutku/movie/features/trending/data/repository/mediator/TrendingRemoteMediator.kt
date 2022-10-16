package com.azizutku.movie.features.trending.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingLocalDataSource
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingRemoteDataSource
import com.azizutku.movie.features.trending.domain.model.TrendingMovieRemoteToLocalMapper
import timber.log.Timber

const val TMDB_FIRST_PAGE_INDEX = 1
const val LAST_PAGE = 1000
const val BASE_VALUE = 10

@OptIn(ExperimentalPagingApi::class)
class TrendingRemoteMediator(
    private val trendingRemoteDataSource: TrendingRemoteDataSource,
    private val trendingLocalDataSource: TrendingLocalDataSource,
    private val remoteToLocalMapper: TrendingMovieRemoteToLocalMapper,
) : RemoteMediator<Int, TrendingMovieEntity>() {

    @Suppress("ReturnCount")
    override suspend fun load(loadType: LoadType, state: PagingState<Int, TrendingMovieEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        } ?: 1

        Timber.i("Fetching trending movies on page $page from remote")
        trendingRemoteDataSource.getTrendingMovies(
            page = page
        ).onSuccess { trendingDto ->
            val movies = trendingDto.movies.mapIndexed { index, movie ->
                /* It is required to keep order of items because it
                   shows trending movies and the order is important.
                   Otherwise, Room orders items by their ids */
                remoteToLocalMapper.map(movie).copy(order = page.minus(1) * BASE_VALUE + index)
            }
            trendingLocalDataSource.refreshDataForPaging(loadType, page, movies)
            return MediatorResult.Success(endOfPaginationReached = movies.size < state.config.pageSize)
        }.onFailure {
            return MediatorResult.Error(it)
        }
        return MediatorResult.Error(Exception())
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TrendingMovieEntity>
    ): TrendingMovieRemoteKeyEntity? = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
        ?.let { movie ->
            trendingLocalDataSource.getRemoteKeyFromDb(movie.id)
        }
}
