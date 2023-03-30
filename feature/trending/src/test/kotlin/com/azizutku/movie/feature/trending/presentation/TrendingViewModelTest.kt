package com.azizutku.movie.feature.trending.presentation

import androidx.paging.PagingData
import app.cash.turbine.test
import com.azizutku.movie.core.testing.fakes.trending.FakeTrendingRemoteDataSourceImpl
import com.azizutku.movie.feature.trending.data.repository.TrendingRepositoryImpl
import com.azizutku.movie.feature.trending.data.repository.datasource.fakes.FakeTrendingLocalDataSourceImpl
import com.azizutku.movie.feature.trending.domain.model.TrendingMovie
import com.azizutku.movie.feature.trending.domain.model.TrendingMovieRemoteToLocalMapper
import com.azizutku.movie.feature.trending.domain.model.TrendingMoviesLocalMapper
import com.azizutku.movie.feature.trending.domain.usecase.GetTrendingMoviesUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class TrendingViewModelTest {

    @get:Rule
    val coroutineRule = com.azizutku.movie.core.testing.util.CoroutineRule()

    private lateinit var viewModel: TrendingViewModel

    private lateinit var fakeTrendingLocalDataSourceImpl: FakeTrendingLocalDataSourceImpl
    private lateinit var fakeTrendingRemoteDataSourceImpl: FakeTrendingRemoteDataSourceImpl

    @Before
    fun setUp() {
        fakeTrendingLocalDataSourceImpl = FakeTrendingLocalDataSourceImpl()
        fakeTrendingRemoteDataSourceImpl = FakeTrendingRemoteDataSourceImpl()

        val trendingRepository = TrendingRepositoryImpl(
            remoteDataSource = fakeTrendingRemoteDataSourceImpl,
            localDataSource = fakeTrendingLocalDataSourceImpl,
            remoteToLocalMapper = TrendingMovieRemoteToLocalMapper(),
            localMapper = TrendingMoviesLocalMapper(),
        )

        viewModel = TrendingViewModel(
            GetTrendingMoviesUseCase(trendingRepository),
        )
    }

    @Test
    fun `creating TrendingViewModel goes through expected ui states`() = runTest {
        fakeTrendingRemoteDataSourceImpl.isSuccessful = false
        launch {
            viewModel.uiState.test {
                with(awaitItem()) {
                    assert(this is TrendingUiState.Success)
                    assertEquals((this as TrendingUiState.Success).pagingData, PagingData.empty<TrendingMovie>())
                }
                with(awaitItem()) {
                    assert(this is TrendingUiState.Success)
                    assertNotEquals((this as TrendingUiState.Success).pagingData, PagingData.empty<TrendingMovie>())
                }
            }
        }
        runCurrent()
    }
}
