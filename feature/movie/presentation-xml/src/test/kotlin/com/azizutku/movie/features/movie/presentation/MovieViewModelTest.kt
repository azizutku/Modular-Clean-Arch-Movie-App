package com.azizutku.movie.features.movie.presentation

import app.cash.turbine.test
import com.azizutku.movie.core.domain.watchlist.usecase.AddMovieToWatchlistUseCase
import com.azizutku.movie.core.domain.watchlist.usecase.CheckMovieInWatchlistUseCase
import com.azizutku.movie.core.domain.watchlist.usecase.RemoveMovieFromWatchlistUseCase
import com.azizutku.movie.core.model.watchlist.MovieWatchlistState
import com.azizutku.movie.core.testing.fakes.watchlist.FakeWatchlistRepositoryImpl
import com.azizutku.movie.core.testing.models.movieDto
import com.azizutku.movie.core.testing.models.movieEntity
import com.azizutku.movie.core.testing.models.watchlistEntity1
import com.azizutku.movie.core.testing.models.watchlistEntity2
import com.azizutku.movie.core.testing.util.CoroutineRule
import com.azizutku.movie.feature.movie.data.repository.MovieRepositoryImpl
import com.azizutku.movie.feature.movie.domain.model.MovieLocalMapper
import com.azizutku.movie.feature.movie.domain.model.MovieRemoteToLocalMapper
import com.azizutku.movie.feature.movie.domain.usecase.GetMovieUseCase
import com.azizutku.movie.feature.movie.presentation.MovieUiState
import com.azizutku.movie.feature.movie.presentation.MovieViewModel
import com.azizutku.movie.features.movie.data.repository.datasource.fakes.FakeMovieCacheDataSourceImpl
import com.azizutku.movie.features.movie.data.repository.datasource.fakes.FakeMovieLocalDataSourceImpl
import com.azizutku.movie.features.movie.data.repository.datasource.fakes.FakeMovieRemoteDataSourceImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: MovieViewModel

    private lateinit var fakeWatchlistRepositoryImpl: FakeWatchlistRepositoryImpl

    private lateinit var fakeMovieCacheDataSourceImpl: FakeMovieCacheDataSourceImpl
    private lateinit var fakeMovieLocalDataSourceImpl: FakeMovieLocalDataSourceImpl
    private lateinit var fakeMovieRemoteDataSourceImpl: FakeMovieRemoteDataSourceImpl

    @Before
    fun setUp() {
        fakeWatchlistRepositoryImpl = FakeWatchlistRepositoryImpl()
        fakeMovieCacheDataSourceImpl =
            FakeMovieCacheDataSourceImpl()
        fakeMovieLocalDataSourceImpl =
            FakeMovieLocalDataSourceImpl()
        fakeMovieRemoteDataSourceImpl =
            FakeMovieRemoteDataSourceImpl()

        val movieRepository = MovieRepositoryImpl(
            remoteDataSource = fakeMovieRemoteDataSourceImpl,
            localDataSource = fakeMovieLocalDataSourceImpl,
            cacheDataSource = fakeMovieCacheDataSourceImpl,
            remoteToLocalMapper = MovieRemoteToLocalMapper(),
            localMapper = MovieLocalMapper(),
        )

        viewModel = MovieViewModel(
            GetMovieUseCase(movieRepository),
            AddMovieToWatchlistUseCase(fakeWatchlistRepositoryImpl),
            RemoveMovieFromWatchlistUseCase(fakeWatchlistRepositoryImpl),
            CheckMovieInWatchlistUseCase(fakeWatchlistRepositoryImpl),
        )
    }

    @Test
    fun `calling getMovie() with cache data goes through expected ui states`() = runTest {
        val movie = MovieLocalMapper().map(movieEntity)
        fakeMovieCacheDataSourceImpl.initWithInitialList(
            listOf(movie)
        )

        assert(viewModel.uiState.value is MovieUiState.Empty)

        launch {
            viewModel.uiState.test {
                assert(awaitItem() is MovieUiState.Empty)
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    assertEquals((this as MovieUiState.Success).movie, movie)
                }
            }
        }
        runCurrent()

        viewModel.getMovie(movie.id)
        runCurrent()
    }

    @Test
    fun `calling getMovie() with local data goes through expected ui states`() = runTest {
        val movie = MovieLocalMapper().map(movieEntity)
        fakeMovieLocalDataSourceImpl.initWithInitialList(
            listOf(movieEntity)
        )

        assert(viewModel.uiState.value is MovieUiState.Empty)

        launch {
            viewModel.uiState.test {
                assert(awaitItem() is MovieUiState.Empty)
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    assertEquals((this as MovieUiState.Success).movie, movie)
                }
            }
        }
        runCurrent()

        viewModel.getMovie(movie.id)
        runCurrent()
    }

    @Test
    fun `calling getMovie() with remote data goes through expected ui states`() = runTest {
        val movieEntity = MovieRemoteToLocalMapper().map(movieDto)
        val movie = MovieLocalMapper().map(movieEntity)

        assert(viewModel.uiState.value is MovieUiState.Empty)

        launch {
            viewModel.uiState.test {
                assert(awaitItem() is MovieUiState.Empty)
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    assertEquals((this as MovieUiState.Success).movie, movie)
                }
            }
        }
        runCurrent()

        viewModel.getMovie(movie.id)
        runCurrent()
    }

    @Test
    fun `calling getMovie() with remote data without success goes through expected ui states`() = runTest {
        val movie = MovieRemoteToLocalMapper().map(movieDto)
        fakeMovieRemoteDataSourceImpl.isSuccessful = false

        assert(viewModel.uiState.value is MovieUiState.Empty)

        launch {
            viewModel.uiState.test {
                assert(awaitItem() is MovieUiState.Empty)
            }
        }
        launch {
            viewModel.stateError.test {
                awaitItem() // There should be error and code should be able to access the line below
                assert(true)
            }
        }
        runCurrent()

        viewModel.getMovie(movie.id)
        runCurrent()
    }

    @Test
    fun `calling isInWatchlist() goes through expected ui states`() = runTest {
        assert(viewModel.uiState.value is MovieUiState.Empty)
        fakeWatchlistRepositoryImpl.watchlistMoviesMap[watchlistEntity1.movieId] = watchlistEntity1
        launch {
            viewModel.uiState.test {
                // For the first call
                assert(awaitItem() is MovieUiState.Empty)
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    val expectedStatus = MovieWatchlistState(
                        isInWatchlist = true
                    )
                    assertEquals((this as MovieUiState.Success).isMovieInWatchlist, expectedStatus)
                }
                // For the second call
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    val expectedStatus = MovieWatchlistState(
                        isInWatchlist = false
                    )
                    assertEquals((this as MovieUiState.Success).isMovieInWatchlist, expectedStatus)
                }
            }
        }
        runCurrent()

        viewModel.isInWatchlist(watchlistEntity1.movieId)
        runCurrent()

        viewModel.isInWatchlist(watchlistEntity2.movieId)
        runCurrent()
    }

    @Test
    fun `calling addToWatchlist() adds movie to watchlist`() = runTest {
        launch {
            viewModel.uiState.test {
                // For the first call
                assert(awaitItem() is MovieUiState.Empty)
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    val expectedStatus = MovieWatchlistState(
                        isInWatchlist = true
                    )
                    assertEquals((this as MovieUiState.Success).isMovieInWatchlist, expectedStatus)
                }
            }
        }
        runCurrent()

        viewModel.addToWatchlist(watchlistEntity1.movieId)
        runCurrent()

        assert(fakeWatchlistRepositoryImpl.watchlistMoviesMap.contains(watchlistEntity1.movieId))
    }

    @Test
    fun `calling removeFromWatchlist() removes movie from watchlist`() = runTest {
        fakeWatchlistRepositoryImpl.watchlistMoviesMap[watchlistEntity1.movieId] = watchlistEntity1
        launch {
            viewModel.uiState.test {
                // For the first call
                assert(awaitItem() is MovieUiState.Empty)
                with(awaitItem()) {
                    assert(this is MovieUiState.Success)
                    val expectedStatus = MovieWatchlistState(
                        isInWatchlist = false
                    )
                    assertEquals((this as MovieUiState.Success).isMovieInWatchlist, expectedStatus)
                }
            }
        }
        runCurrent()

        viewModel.removeFromWatchlist(watchlistEntity1.movieId)
        runCurrent()

        assert(fakeWatchlistRepositoryImpl.watchlistMoviesMap.contains(watchlistEntity1.movieId).not())
    }
}
