package com.azizutku.movie.features.trending.di

import com.azizutku.movie.core.common.util.Mapper
import com.azizutku.movie.core.database.model.TrendingMovieEntity
import com.azizutku.movie.features.trending.data.remote.dto.TrendingMovieDto
import com.azizutku.movie.features.trending.data.repository.TrendingRepositoryImpl
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingLocalDataSource
import com.azizutku.movie.features.trending.data.repository.datasource.TrendingRemoteDataSource
import com.azizutku.movie.features.trending.data.repository.datasourceImpl.TrendingLocalDataSourceImpl
import com.azizutku.movie.features.trending.data.repository.datasourceImpl.TrendingRemoteDataSourceImpl
import com.azizutku.movie.features.trending.domain.model.TrendingMovie
import com.azizutku.movie.features.trending.domain.model.TrendingMovieRemoteToLocalMapper
import com.azizutku.movie.features.trending.domain.model.TrendingMoviesLocalMapper
import com.azizutku.movie.features.trending.domain.repository.TrendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class TrendingModule {

    @ViewModelScoped
    @Binds
    abstract fun bindTrendingRepository(repository: TrendingRepositoryImpl): TrendingRepository

    @ViewModelScoped
    @Binds
    abstract fun bindRemoteDataSource(impl: TrendingRemoteDataSourceImpl): TrendingRemoteDataSource

    @ViewModelScoped
    @Binds
    abstract fun bindLocalDataSource(impl: TrendingLocalDataSourceImpl): TrendingLocalDataSource

    @ViewModelScoped
    @Binds
    abstract fun bindLocalMapper(impl: TrendingMoviesLocalMapper): Mapper<TrendingMovieEntity, TrendingMovie>

    @ViewModelScoped
    @Binds
    abstract fun bindRemoteToLocalMapper(
        impl: TrendingMovieRemoteToLocalMapper
    ): Mapper<TrendingMovieDto, TrendingMovieEntity>
}
