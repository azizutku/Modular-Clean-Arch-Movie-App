package com.azizutku.movie.features.trending.repository.mediator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.filters.SmallTest
import com.azizutku.movie.core.database.model.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.remote.dto.TrendingDto
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingLocalDataSource
import com.azizutku.movie.features.trending.data.repository.datasourceImpl.TrendingLocalDataSourceImpl
import com.azizutku.movie.features.trending.data.repository.mediator.TrendingRemoteMediator
import com.azizutku.movie.features.trending.domain.model.TrendingMovieRemoteToLocalMapper
import com.azizutku.movie.features.trending.repository.datasource.FakeTrendingRemoteDataSourceImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class TrendingRemoteMediatorTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: com.azizutku.movie.core.database.MainDatabase

    private lateinit var trendingRemoteDataSource: FakeTrendingRemoteDataSourceImpl
    private lateinit var trendingLocalDataSource: TrendingLocalDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
        trendingRemoteDataSource = FakeTrendingRemoteDataSourceImpl()
        trendingLocalDataSource = TrendingLocalDataSourceImpl(
            trendingMoviesDao = database.trendingMoviesDao(),
            trendingMovieRemoteKeysDao = database.trendingMovieRemoteKeysDao(),
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = TrendingRemoteMediator(
            trendingRemoteDataSource,
            trendingLocalDataSource,
            TrendingMovieRemoteToLocalMapper(),
        )
        val pagingState = PagingState<Int, TrendingMovieEntity>(
            listOf(),
            null,
            PagingConfig(2),
            2,
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        trendingRemoteDataSource.trendingDto = TrendingDto(
            movies = emptyList(),
            totalPages = 1,
            page = 1,
            totalResults = 0,
        )
        val remoteMediator = TrendingRemoteMediator(
            trendingRemoteDataSource,
            trendingLocalDataSource,
            TrendingMovieRemoteToLocalMapper(),
        )
        val pagingState = PagingState<Int, TrendingMovieEntity>(
            listOf(),
            null,
            PagingConfig(2),
            2,
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        trendingRemoteDataSource.isSuccessful = false
        val remoteMediator = TrendingRemoteMediator(
            trendingRemoteDataSource,
            trendingLocalDataSource,
            TrendingMovieRemoteToLocalMapper(),
        )
        val pagingState = PagingState<Int, TrendingMovieEntity>(
            listOf(),
            null,
            PagingConfig(2),
            2,
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun appendLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = TrendingRemoteMediator(
            trendingRemoteDataSource,
            trendingLocalDataSource,
            TrendingMovieRemoteToLocalMapper(),
        )
        val pagingState = PagingState<Int, TrendingMovieEntity>(
            listOf(),
            null,
            PagingConfig(2),
            2,
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}
