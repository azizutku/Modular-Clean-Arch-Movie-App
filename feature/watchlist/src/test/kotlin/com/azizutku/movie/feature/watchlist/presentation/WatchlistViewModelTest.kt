package com.azizutku.movie.feature.watchlist.presentation

import androidx.paging.PagingData
import app.cash.turbine.test
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import com.azizutku.movie.feature.watchlist.data.repository.WatchlistRepositoryImpl
import com.azizutku.movie.core.testing.fakes.watchlist.FakeWatchlistLocalDataSourceImpl
import com.azizutku.movie.feature.watchlist.domain.model.WatchlistMovieLocalMapper
import com.azizutku.movie.feature.watchlist.domain.usecase.GetMoviesFromWatchlistUseCase
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
class WatchlistViewModelTest {

    @get:Rule
    val coroutineRule = com.azizutku.movie.core.testing.util.CoroutineRule()

    private lateinit var viewModel: WatchlistViewModel

    private lateinit var fakeWatchlistLocalDataSourceImpl: FakeWatchlistLocalDataSourceImpl

    @Before
    fun setUp() {
        fakeWatchlistLocalDataSourceImpl = FakeWatchlistLocalDataSourceImpl()
        val favoritesRepository = WatchlistRepositoryImpl(
            localDataSource = fakeWatchlistLocalDataSourceImpl,
            localMapper = WatchlistMovieLocalMapper(),
        )

        viewModel = WatchlistViewModel(
            GetMoviesFromWatchlistUseCase(favoritesRepository),
        )
    }

    @Test
    fun `calling getWatchlistMovies() goes through expected ui states`() = runTest {
        launch {
            viewModel.uiState.test {
                with(awaitItem()) {
                    assert(this is WatchlistUiState.Success)
                    assertEquals((this as WatchlistUiState.Success).pagingData, PagingData.empty<WatchlistMovie>())
                }
                with(awaitItem()) {
                    assert(this is WatchlistUiState.Success)
                    assertNotEquals((this as WatchlistUiState.Success).pagingData, PagingData.empty<WatchlistMovie>())
                }
            }
        }
        runCurrent()

        viewModel.getMoviesFromWatchlist()
        runCurrent()
    }
}
