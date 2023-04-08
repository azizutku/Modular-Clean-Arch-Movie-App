package com.azizutku.movie.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.azizutku.movie.core.database.model.MovieEntity

@Dao
interface MoviesDao {
    @Upsert
    suspend fun upsert(entity: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieEntity?
}
