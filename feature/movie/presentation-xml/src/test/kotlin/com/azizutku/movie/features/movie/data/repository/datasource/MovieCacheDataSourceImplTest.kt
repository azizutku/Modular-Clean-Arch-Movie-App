package com.azizutku.movie.features.movie.data.repository.datasource

import com.azizutku.movie.core.testing.models.movie
import com.azizutku.movie.feature.movie.data.repository.datasourceImpl.MovieCacheDataSourceImpl
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class MovieCacheDataSourceImplTest {

    private lateinit var movieCacheDataSource: MovieCacheDataSourceImpl

    @Before
    fun setUp() {
        movieCacheDataSource = MovieCacheDataSourceImpl()
    }

    @Test
    fun `when calling getMovieFromCache with movie that is in cache, return movie from cache`() {
        // Arrange
        val expectedMovie = movie
        movieCacheDataSource.saveMovieToCache(expectedMovie)

        // Act
        val actualMovie = movieCacheDataSource.getMovieFromCache(expectedMovie.id)

        // Assert
        assertEquals(expectedMovie, actualMovie)
    }

    @Test
    fun `when calling getMovieFromCache with movie that is not in cache, return null`() {
        // Arrange
        val expectedMovie = null

        // Act
        val actualMovie = movieCacheDataSource.getMovieFromCache(0)

        // Assert
        assertEquals(expectedMovie, actualMovie)
    }
}
