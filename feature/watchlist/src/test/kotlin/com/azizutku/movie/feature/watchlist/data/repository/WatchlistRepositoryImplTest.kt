package com.azizutku.movie.feature.watchlist.data.repository

import android.util.Log
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.azizutku.movie.core.common.extensions.orFalse
import com.azizutku.movie.core.common.extensions.orTrue
import com.azizutku.movie.core.common.vo.DataState
import com.azizutku.movie.core.database.model.WatchlistEntity
import com.azizutku.movie.core.testing.fakes.watchlist.FakeWatchlistLocalDataSourceImpl
import com.azizutku.movie.core.testing.models.movieEntity
import com.azizutku.movie.core.testing.models.movieEntity2
import com.azizutku.movie.core.testing.util.CoroutineRule
import com.azizutku.movie.feature.watchlist.domain.model.WatchlistMovieLocalMapper
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class WatchlistRepositoryImplTest {
    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var watchlistRepository: WatchlistRepositoryImpl
    private lateinit var fakeWatchlistLocalDataSourceImpl: FakeWatchlistLocalDataSourceImpl

    @Before
    fun setUp() {
        fakeWatchlistLocalDataSourceImpl = FakeWatchlistLocalDataSourceImpl()
        watchlistRepository = WatchlistRepositoryImpl(
            localDataSource = fakeWatchlistLocalDataSourceImpl,
            localMapper = WatchlistMovieLocalMapper(),
        )
        mockkStatic(Log::isLoggable)
        every { Log.isLoggable(any(), any()) } returns false
    }

    @Test
    fun `calling addMovie emits expected states and adds movie to local cache`() = runTest {
        // Arrange
        val movieId = 1

        // Act
        val addMovieFlow = watchlistRepository.addMovie(movieId)

        // Assert
        launch {
            addMovieFlow.test {
                assert(awaitItem() is DataState.Loading)
                val dataState = awaitItem()
                assert(dataState is DataState.Success)
                assert((dataState as? DataState.Success)?.data?.isInWatchlist.orFalse())
                cancelAndIgnoreRemainingEvents()
            }
            assert(fakeWatchlistLocalDataSourceImpl.isMovieInWatchlist(movieId = movieId))
        }
        runCurrent()
    }

    @Test
    fun `calling removeMovie emits expected states and removes movie from local cache`() =
        runTest {
            // Arrange
            val movieId = 1
            watchlistRepository.addMovie(movieId)

            // Act
            val removeMovieFlow = watchlistRepository.removeMovie(movieId)

            // Assert
            launch {
                removeMovieFlow.test {
                    assert(awaitItem() is DataState.Loading)
                    val dataState = awaitItem()
                    assert(dataState is DataState.Success)
                    assertFalse((dataState as? DataState.Success)?.data?.isInWatchlist.orTrue())
                    cancelAndIgnoreRemainingEvents()
                }
                assertFalse(fakeWatchlistLocalDataSourceImpl.isMovieInWatchlist(movieId = movieId))
            }
            runCurrent()
        }

    @Test
    fun `calling getAllMovies emits expected states and retrieves all movies from local cache`() =
        runTest {
            // Arrange
            val movieEntities = listOf(
                movieEntity,
                movieEntity2,
            )
            fakeWatchlistLocalDataSourceImpl.movieEntitiesForPagingSource = movieEntities

            // Act
            val getAllMovieFlow = watchlistRepository.getAllMovies()

            // Assert
            launch {
                val watchlistMovies = getAllMovieFlow.asSnapshot()
                assertEquals(
                    expected = movieEntities.map {
                        WatchlistMovieLocalMapper().map(it)
                    },
                    actual = watchlistMovies,
                )
            }
            runCurrent()
        }

    @Test
    fun `calling isMovieInWatchlist emits expected states for movie present in watchlist`() =
        runTest {
            // Arrange
            val movieId = 1
            fakeWatchlistLocalDataSourceImpl.addToWatchlist(
                WatchlistEntity(
                    movieId = movieId,
                    addedAt = System.currentTimeMillis(),
                ),
            )

            // Act
            val isMovieInWatchlistFlow = watchlistRepository.isMovieInWatchlist(movieId)

            // Assert
            launch {
                isMovieInWatchlistFlow.test {
                    assert(awaitItem() is DataState.Loading)
                    val dataState = awaitItem()
                    assert(dataState is DataState.Success)
                    assert((dataState as? DataState.Success)?.data?.isInWatchlist.orFalse())
                    cancelAndIgnoreRemainingEvents()
                }
            }
            runCurrent()
        }

    @Test
    fun `calling isMovieInWatchlist emits expected states for movie absent from watchlist`() =
        runTest {
            // Arrange
            val movieId = 1

            // Act
            val isMovieInWatchlistFlow = watchlistRepository.isMovieInWatchlist(movieId)

            // Assert
            launch {
                isMovieInWatchlistFlow.test {
                    assert(awaitItem() is DataState.Loading)
                    val dataState = awaitItem()
                    assert(dataState is DataState.Success)
                    assertFalse((dataState as? DataState.Success)?.data?.isInWatchlist.orTrue())
                    cancelAndIgnoreRemainingEvents()
                }
            }
            runCurrent()
        }
}
