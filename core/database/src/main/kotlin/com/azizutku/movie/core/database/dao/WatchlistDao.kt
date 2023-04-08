package com.azizutku.movie.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.core.database.model.WatchlistEntity

@Dao
interface WatchlistDao {

    @Upsert
    suspend fun upsert(entity: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE movie_id = :movieId")
    suspend fun delete(movieId: Int)

    @Query(
        "SELECT movies.* FROM movies INNER JOIN watchlist ON movies.id = watchlist.movie_id " +
                "ORDER BY watchlist.added_at DESC"
    )
    fun getAllMoviesInWatchlist(): PagingSource<Int, MovieEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE movie_id = :movieId LIMIT 1)")
    suspend fun isMovieExist(movieId: Int): Boolean
}
