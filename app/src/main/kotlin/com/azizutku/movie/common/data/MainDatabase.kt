package com.azizutku.movie.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azizutku.movie.features.movie.data.local.MoviesDao
import com.azizutku.movie.features.movie.data.local.entity.MovieEntity
import com.azizutku.movie.features.trending.data.local.TrendingMovieRemoteKeysDao
import com.azizutku.movie.features.trending.data.local.TrendingMoviesDao
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.features.watchlist.data.local.WatchlistDao
import com.azizutku.movie.features.watchlist.data.local.entity.WatchlistEntity

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
