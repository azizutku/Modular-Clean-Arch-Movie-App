package com.azizutku.movie.feature.trending.presentation

import android.util.Log
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.azizutku.movie.core.testing.fakes.trending.FakeTrendingRemoteDataSourceImpl
import com.azizutku.movie.core.testing.models.trendingMovieEntity
import com.azizutku.movie.core.testing.models.trendingMovieEntity2
import com.azizutku.movie.core.testing.util.CoroutineRule
import com.azizutku.movie.feature.trending.common.data.repository.TrendingRepositoryImpl
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovieRemoteToLocalMapper
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMoviesLocalMapper
import com.azizutku.movie.feature.trending.common.domain.usecase.GetTrendingMoviesUseCase
import com.azizutku.movie.feature.trending.common.presentation.TrendingUiState
import com.azizutku.movie.feature.trending.common.presentation.TrendingViewModel
import com.azizutku.movie.feature.trending.testing.fakes.FakeTrendingLocalDataSourceImpl
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
        mockkStatic(Log::isLoggable)
        every { Log.isLoggable(any(), any()) } returns false
        viewModel = TrendingViewModel(
            GetTrendingMoviesUseCase(trendingRepository),
        )
    }

    @Test
    fun `creating TrendingViewModel goes through expected ui states`() = runTest {
        // Arrange
        val trendingMovieEntities = listOf(
            trendingMovieEntity,
            trendingMovieEntity2,
        )
        fakeTrendingRemoteDataSourceImpl.isSuccessful = true
        fakeTrendingLocalDataSourceImpl.trendingMovieEntities = trendingMovieEntities

        // Assert
        launch {
            viewModel.uiState.test {
                with(awaitItem()) {
                    assert(this is TrendingUiState.Success)
                    val pagingDataContent = flow {
                        emit((this@with as TrendingUiState.Success).pagingData)
                    }.asSnapshot()
                    assert(pagingDataContent.isEmpty())
                }
                with(awaitItem()) {
                    assert(this is TrendingUiState.Success)
                    val pagingDataContent = flow {
                        emit((this@with as TrendingUiState.Success).pagingData)
                    }.asSnapshot()
                    assertEquals(
                        pagingDataContent,
                        trendingMovieEntities.map {
                            TrendingMoviesLocalMapper().map(it)
                        },
                    )
                }
            }
        }
        runCurrent()
    }
}
