package com.azizutku.feature.watchlist.common.presentation

import android.util.Log
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.azizutku.feature.watchlist.common.data.repository.WatchlistRepositoryImpl
import com.azizutku.feature.watchlist.common.domain.model.WatchlistMovieLocalMapper
import com.azizutku.feature.watchlist.common.domain.usecase.GetMoviesFromWatchlistUseCase
import com.azizutku.movie.core.testing.fakes.watchlist.FakeWatchlistLocalDataSourceImpl
import com.azizutku.movie.core.testing.models.movieEntity
import com.azizutku.movie.core.testing.models.movieEntity2
import com.azizutku.movie.core.testing.util.CoroutineRule
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class WatchlistViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: WatchlistViewModel

    private lateinit var fakeWatchlistLocalDataSourceImpl: FakeWatchlistLocalDataSourceImpl

    @Before
    fun setUp() {
        fakeWatchlistLocalDataSourceImpl = FakeWatchlistLocalDataSourceImpl()
        val favoritesRepository = WatchlistRepositoryImpl(
            localDataSource = fakeWatchlistLocalDataSourceImpl,
            localMapper = WatchlistMovieLocalMapper(),
        )
        mockkStatic(Log::isLoggable)
        every { Log.isLoggable(any(), any()) } returns false
        viewModel = WatchlistViewModel(
            GetMoviesFromWatchlistUseCase(favoritesRepository),
        )
    }

    @Test
    fun `calling getWatchlistMovies() goes through expected ui states`() = runTest {
        // Arrange
        val movieEntities = listOf(movieEntity, movieEntity2)
        fakeWatchlistLocalDataSourceImpl.movieEntitiesForPagingSource = movieEntities

        // Act
        viewModel.getMoviesFromWatchlist()

        // Assert
        launch {
            viewModel.uiState.test {
                with(awaitItem()) {
                    assert(this is WatchlistUiState.Success)
                    val pagingDataContent = flow {
                        emit((this@with as WatchlistUiState.Success).pagingData)
                    }.asSnapshot()
                    assert(pagingDataContent.isEmpty())
                }
                with(awaitItem()) {
                    assert(this is WatchlistUiState.Success)
                    val pagingDataContent = flow {
                        emit((this@with as WatchlistUiState.Success).pagingData)
                    }.asSnapshot()
                    assertEquals(
                        pagingDataContent,
                        movieEntities.map {
                            WatchlistMovieLocalMapper().map(it)
                        },
                    )
                }
            }
        }
        runCurrent()
    }
}
