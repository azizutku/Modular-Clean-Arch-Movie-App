package com.azizutku.movie.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azizutku.movie.core.database.dao.MoviesDao
import com.azizutku.movie.core.database.dao.TrendingMovieRemoteKeysDao
import com.azizutku.movie.core.database.dao.TrendingMoviesDao
import com.azizutku.movie.core.database.dao.WatchlistDao
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.TrendingMovieEntity
import com.azizutku.movie.core.database.model.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.core.database.model.WatchlistEntity

@Database(
    entities = [
        TrendingMovieEntity::class,
        TrendingMovieRemoteKeyEntity::class,
        MovieEntity::class,
        WatchlistEntity::class,
    ],
    version = 1,
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingMoviesDao
    abstract fun trendingMovieRemoteKeysDao(): TrendingMovieRemoteKeysDao
    abstract fun moviesDao(): MoviesDao
    abstract fun watchlistDao(): WatchlistDao
}
