package com.azizutku.movie.features.watchlist.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "added_at")
    val addedAt: Long,
)
