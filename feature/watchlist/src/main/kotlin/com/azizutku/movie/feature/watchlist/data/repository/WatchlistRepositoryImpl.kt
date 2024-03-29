package com.azizutku.movie.feature.watchlist.data.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.database.model.WatchlistEntity
import com.azizutku.movie.core.domain.watchlist.repository.WatchlistRepository
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import com.azizutku.movie.feature.watchlist.data.repository.datasource.WatchlistLocalDataSource
import com.azizutku.movie.feature.watchlist.domain.model.WatchlistMovieLocalMapper
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@VisibleForTesting
const val PAGE_SIZE = 20

@VisibleForTesting
const val INITIAL_LOAD_SIZE = 20

@VisibleForTesting
const val PREFETCH_DISTANCE = 6

class WatchlistRepositoryImpl @Inject constructor(
    private val localDataSource: WatchlistLocalDataSource,
    private val localMapper: WatchlistMovieLocalMapper,
) : WatchlistRepository {

    override fun addMovie(movieId: Int): Flow<DataState<MovieWatchlistState>> = flow {
        emit(DataState.Loading)
        localDataSource.addToWatchlist(
            WatchlistEntity(
                movieId = movieId,
                addedAt = System.currentTimeMillis(),
            )
        )
        emit(
            DataState.Success(
                MovieWatchlistState(
                    isInWatchlist = true,
                )
            )
        )
        Timber.i("Movie with id $movieId was added to watchlist")
    }

    override fun removeMovie(movieId: Int): Flow<DataState<MovieWatchlistState>> = flow {
        emit(DataState.Loading)
        localDataSource.removeFromWatchlist(movieId)
        emit(
            DataState.Success(
                MovieWatchlistState(
                    isInWatchlist = false,
                )
            )
        )
        Timber.i("Movie with id $movieId was removed from watchlist")
    }

    override fun getAllMovies(): Flow<PagingData<WatchlistMovie>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INITIAL_LOAD_SIZE,
        ),
        pagingSourceFactory = { localDataSource.getAllMoviesInWatchlist() }
    ).flow.map { pagingData ->
        pagingData.map {
            localMapper.map(it)
        }
    }

    override fun isMovieInWatchlist(movieId: Int): Flow<DataState<MovieWatchlistState>> = flow {
        emit(DataState.Loading)
        val isExist = localDataSource.isMovieInWatchlist(movieId)
        emit(
            DataState.Success(
                MovieWatchlistState(
                    isInWatchlist = isExist,
                )
            )
        )
        Timber.i("Movie with id $movieId is in watchlist: $isExist")
    }
}
