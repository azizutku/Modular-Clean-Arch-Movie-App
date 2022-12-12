package com.azizutku.movie.features.watchlist.presentation

import androidx.paging.PagingData
import app.cash.turbine.test
import com.azizutku.movie.features.watchlist.data.repository.WatchlistRepositoryImpl
import com.azizutku.movie.features.watchlist.data.repository.datasource.FakeWatchlistLocalDataSourceImpl
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovieLocalMapper
import com.azizutku.movie.features.watchlist.domain.usecase.GetMoviesFromWatchlistUseCase
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
