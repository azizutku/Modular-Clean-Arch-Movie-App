package com.azizutku.movie.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azizutku.movie.features.trending.data.local.TrendingMovieRemoteKeysDao
import com.azizutku.movie.features.trending.data.local.TrendingMoviesDao
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieRemoteKeyEntity

@Database(
    entities = [
        TrendingMovieEntity::class,
        TrendingMovieRemoteKeyEntity::class,
    ],
    version = 1,
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingMoviesDao
    abstract fun trendingMovieRemoteKeysDao(): TrendingMovieRemoteKeysDao
}
