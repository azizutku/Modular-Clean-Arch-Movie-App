package com.azizutku.movie.features.watchlist.repository.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.test.filters.SmallTest
import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.features.watchlist.data.repository.datasource.WatchlistLocalDataSource
import com.azizutku.movie.features.watchlist.data.repository.datasourceImpl.WatchlistLocalDataSourceImpl
import com.azizutku.movie.models.movieEntity
import com.azizutku.movie.models.watchlistEntity1
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class WatchlistLocalDataSourceImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: MainDatabase

    private lateinit var watchlistLocalDataSource: WatchlistLocalDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
        watchlistLocalDataSource = WatchlistLocalDataSourceImpl(database.watchlistDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun canSaveWatchlistEntityToDatabaseAndReadIt() = runTest {
        watchlistLocalDataSource.addToWatchlist(watchlistEntity1)
        val isMovieInWatchlist = watchlistLocalDataSource.isMovieInWatchlist(watchlistEntity1.movieId)
        assert(isMovieInWatchlist)
    }

    @Test
    fun canRemoveWatchlistEntityFromDatabaseSuccessfully() = runTest {
        watchlistLocalDataSource.addToWatchlist(watchlistEntity1)
        watchlistLocalDataSource.removeFromWatchlist(watchlistEntity1.movieId)
        val isMovieInWatchlist = watchlistLocalDataSource.isMovieInWatchlist(watchlistEntity1.movieId)
        assert(isMovieInWatchlist.not())
    }

    @Test
    fun canReadWatchlistWithPaging() = runTest {
        database.moviesDao().insert(movieEntity)
        watchlistLocalDataSource.addToWatchlist(watchlistEntity1)

        val pagingSource = watchlistLocalDataSource.getAllMoviesInWatchlist()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assert(loadResult is PagingSource.LoadResult.Page)
        assert((loadResult as PagingSource.LoadResult.Page).data.isNotEmpty())
        assertEquals(watchlistEntity1.movieId, loadResult.data[0].id)
        assertEquals(movieEntity, loadResult.data[0])
    }
}
