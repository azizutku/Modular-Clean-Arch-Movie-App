package com.azizutku.movie.features.trending.data.local

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.local.entity.TrendingMovieRemoteKeyEntity
import com.azizutku.movie.features.trending.data.repository.mediator.LAST_PAGE
import com.azizutku.movie.features.trending.data.repository.mediator.TMDB_FIRST_PAGE_INDEX

@Dao
interface TrendingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TrendingMovieEntity>)

    @Query("SELECT * FROM trending_movies ORDER BY `order` ASC")
    fun getPagingSource(): PagingSource<Int, TrendingMovieEntity>

    @Query("DELETE FROM trending_movies")
    suspend fun clearAll()

    @Transaction
    suspend fun deleteAndInsertTransactionForPaging(
        loadType: LoadType,
        page: Int,
        movies: List<TrendingMovieEntity>,
        trendingMovieRemoteKeysDao: TrendingMovieRemoteKeysDao
    ) {
        val endOfPaginationReached = page == LAST_PAGE
        if (loadType == LoadType.REFRESH) {
            trendingMovieRemoteKeysDao.clearAll()
            clearAll()
        }
        val prevKey = page.minus(1).takeUnless { page == TMDB_FIRST_PAGE_INDEX }
        val nextKey = page.plus(1).takeUnless { endOfPaginationReached }
        val keys = movies.map {
            TrendingMovieRemoteKeyEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
        }
        trendingMovieRemoteKeysDao.insertAll(keys)
        insertAll(movies)
    }
}
