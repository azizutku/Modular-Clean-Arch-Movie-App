package com.azizutku.movie.feature.movie.data.repository

import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieCacheDataSource
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieLocalDataSource
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieRemoteDataSource
import com.azizutku.movie.feature.movie.domain.model.Movie
import com.azizutku.movie.feature.movie.domain.model.MovieLocalMapper
import com.azizutku.movie.feature.movie.domain.model.MovieRemoteToLocalMapper
import com.azizutku.movie.feature.movie.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val cacheDataSource: MovieCacheDataSource,
    private val remoteToLocalMapper: MovieRemoteToLocalMapper,
    private val localMapper: MovieLocalMapper,
) : MovieRepository {

    override fun getMovie(movieId: Int): Flow<DataState<Movie>> = flow {
        emit(DataState.Loading)

        val movieFromCache = getMovieFromCache(movieId)
        if (movieFromCache != null) {
            Timber.i("Emitting movie with id $movieId from cache")
            emit(DataState.Success(movieFromCache))
            emitAll(getMovieDataFlowFromRemote(movieId, false))
            return@flow
        }

        val movieFromDb = getMovieFromDb(movieId)
        if (movieFromDb != null) {
            Timber.i("Emitting movie with id $movieId from db")
            emit(DataState.Success(movieFromDb))
            emitAll(getMovieDataFlowFromRemote(movieId, false))
            return@flow
        }

        emitAll(getMovieDataFlowFromRemote(movieId))
    }

    private fun getMovieFromCache(movieId: Int): Movie? = cacheDataSource.getMovieFromCache(movieId)

    private suspend fun getMovieFromDb(movieId: Int): Movie? = localDataSource.getMovie(movieId)?.let { movieEntity ->
        localMapper.map(movieEntity)
    }

    private suspend fun getMovieDataFlowFromRemote(
        movieId: Int,
        emitError: Boolean = true,
    ): Flow<DataState<Movie>> = flow {
        remoteDataSource.getMovie(movieId).onSuccess { movieDto ->
            Timber.i("Emitting from movie with id $movieId from remote")
            val movieEntity = remoteToLocalMapper.map(movieDto)
            val movie = localMapper.map(movieEntity)
            emit(DataState.Success(movie))
            localDataSource.insertMovieToDb(movieEntity)
            cacheDataSource.saveMovieToCache(movie)
        }.onFailure {
            if (emitError.not()) {
                return@onFailure
            }
            val exception = it as? NetworkException
            exception ?: return@onFailure
            emit(DataState.Error(exception))
        }
    }
}
