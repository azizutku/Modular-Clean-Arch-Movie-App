package com.azizutku.movie.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.azizutku.movie.core.database.model.TrendingMovieRemoteKeyEntity

@Dao
interface TrendingMovieRemoteKeysDao {

    @Upsert
    suspend fun upsertAll(remoteKey: List<TrendingMovieRemoteKeyEntity>)

    @Query("SELECT * FROM trending_movie_remote_keys WHERE id = :movieId")
    suspend fun getRemoteKey(movieId: Int): TrendingMovieRemoteKeyEntity?

    @Query("DELETE FROM trending_movie_remote_keys")
    suspend fun clearAll()
}
