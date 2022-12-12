package com.azizutku.movie.features.trending.presentation

import androidx.paging.PagingData
import app.cash.turbine.test
import com.azizutku.movie.features.trending.data.repository.TrendingRepositoryImpl
import com.azizutku.movie.features.trending.data.repository.datasource.FakeTrendingLocalDataSourceImpl
import com.azizutku.movie.features.trending.data.repository.datasource.FakeTrendingRemoteDataSourceImpl
import com.azizutku.movie.features.trending.domain.model.TrendingMovie
import com.azizutku.movie.features.trending.domain.model.TrendingMovieRemoteToLocalMapper
import com.azizutku.movie.features.trending.domain.model.TrendingMoviesLocalMapper
import com.azizutku.movie.features.trending.domain.usecase.GetTrendingMoviesUseCase
import com.azizutku.movie.util.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TrendingViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

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
