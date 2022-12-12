package com.azizutku.movie.features.movie.repository.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.azizutku.movie.common.data.MainDatabase
import com.azizutku.movie.features.movie.data.repository.datasource.MovieLocalDataSource
import com.azizutku.movie.features.movie.data.repository.datasourceImpl.MovieLocalDataSourceImpl
import com.azizutku.movie.models.movieEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
class MovieLocalDataSourceImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: MainDatabase

    private lateinit var movieLocalDataSource: MovieLocalDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
        movieLocalDataSource = MovieLocalDataSourceImpl(database.moviesDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun canSaveMovieToDatabaseAndReadIt() = runTest {
        movieLocalDataSource.insertMovieToDb(movieEntity)

        assert(movieLocalDataSource.getMovie(movieEntity.id) == movieEntity)
    }

    @Test
    fun canDeleteMovieFromDatabase() = runTest {
        movieLocalDataSource.insertMovieToDb(movieEntity)

        movieLocalDataSource.clearAllMoviesFromDb()

        assert(movieLocalDataSource.getMovie(movieEntity.id) == null)
    }
}
