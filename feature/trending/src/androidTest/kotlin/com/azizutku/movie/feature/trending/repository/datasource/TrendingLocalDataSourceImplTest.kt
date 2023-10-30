package com.azizutku.movie.feature.trending.repository.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.test.filters.SmallTest
import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.core.database.model.TrendingMovieEntity
import com.azizutku.movie.core.database.model.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.feature.trending.data.repository.datasource.TrendingLocalDataSource
import com.azizutku.movie.feature.trending.data.repository.datasourceImpl.TrendingLocalDataSourceImpl
import com.azizutku.movie.core.testing.models.trendingMovieEntity
import com.azizutku.movie.core.testing.models.trendingMovieEntity2
import com.azizutku.movie.core.testing.models.trendingMovieEntity3
import com.azizutku.movie.core.testing.models.trendingMovieEntity4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class TrendingLocalDataSourceImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: MainDatabase

    private lateinit var moviesLocalDataSource: TrendingLocalDataSource

    private val trendingMovies = listOf(
        trendingMovieEntity,
        trendingMovieEntity2,
        trendingMovieEntity3,
    )

    private val trendingMovieRemoteKeys = trendingMovies.map {
        TrendingMovieRemoteKeyEntity(
            it.id,
            prevKey = null,
            nextKey = null,
        )
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        moviesLocalDataSource = TrendingLocalDataSourceImpl(
            database.trendingMoviesDao(),
            database.trendingMovieRemoteKeysDao(),
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun canSaveTrendingMoviesToDatabaseAndReadThem() = runTest {
        // Arrange
        moviesLocalDataSource.insertAllMoviesToDb(trendingMovies)

        // Act
        val pagingSource = moviesLocalDataSource.getPagingSourceFromDb()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            ),
        )

        // Assert
        assert(loadResult is PagingSource.LoadResult.Page)
        assertEquals((loadResult as PagingSource.LoadResult.Page).data, trendingMovies)
    }

    @Test
    fun canClearAllTrendingMoviesFromDatabaseSuccessfully() = runTest {
        // Arrange
        moviesLocalDataSource.insertAllMoviesToDb(trendingMovies)
        moviesLocalDataSource.clearAllMoviesFromDb()

        // Act
        val pagingSource = moviesLocalDataSource.getPagingSourceFromDb()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            ),
        )

        // Assert
        assert(loadResult is PagingSource.LoadResult.Page)
        assertEquals(
            (loadResult as PagingSource.LoadResult.Page).data,
            emptyList<TrendingMovieEntity>(),
        )
    }

    @Test
    fun canSaveTrendingMovieRemoteKeysToDatabaseAndReadThem() = runTest {
        // Arrange
        moviesLocalDataSource.insertAllRemoteKeysToDb(trendingMovieRemoteKeys)

        trendingMovieRemoteKeys.forEachIndexed { index, expectedRemoteKeyEntity ->
            // Act
            val actualRemoteKeyEntity =
                moviesLocalDataSource.getRemoteKeyFromDb(trendingMovies[index].id)

            // Assert
            assertEquals(expectedRemoteKeyEntity, actualRemoteKeyEntity)
        }
    }

    @Test
    fun canClearAllTrendingMovieRemoteKeysFromDatabaseSuccessfully() = runTest {
        // Arrange
        val trendingMovieRemoteKeys = listOf(
            TrendingMovieRemoteKeyEntity(
                trendingMovies[0].id,
                prevKey = null,
                nextKey = null,
            ),
        )

        // Act
        moviesLocalDataSource.insertAllRemoteKeysToDb(trendingMovieRemoteKeys)

        // Assert
        // Check database has remote key first.
        assertEquals(
            moviesLocalDataSource.getRemoteKeyFromDb(trendingMovies[0].id),
            trendingMovieRemoteKeys[0],
        )

        // Act
        moviesLocalDataSource.clearAllRemoteKeysFromDb()

        // Assert
        assertEquals(moviesLocalDataSource.getRemoteKeyFromDb(trendingMovies[0].id), null)
    }

    @Test
    fun canRefreshDataForPagingSuccessfully() = runTest {
        // Arrange
        val trendingMovieRemoteKey = TrendingMovieRemoteKeyEntity(
            id = trendingMovieEntity4.id,
            prevKey = null,
            nextKey = null,
        )
        moviesLocalDataSource.insertAllMoviesToDb(listOf(trendingMovieEntity4))
        moviesLocalDataSource.insertAllRemoteKeysToDb(listOf(trendingMovieRemoteKey))

        // Act
        moviesLocalDataSource.refreshDataForPaging(
            loadType = LoadType.REFRESH,
            page = 0,
            movies = trendingMovies,
        )

        // Assert
        assertEquals(moviesLocalDataSource.getRemoteKeyFromDb(trendingMovieEntity4.id), null)

        // Act
        val pagingSource = moviesLocalDataSource.getPagingSourceFromDb()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            ),
        )

        // Assert
        assert(loadResult is PagingSource.LoadResult.Page)
        assertEquals((loadResult as PagingSource.LoadResult.Page).data, trendingMovies)
    }
}
