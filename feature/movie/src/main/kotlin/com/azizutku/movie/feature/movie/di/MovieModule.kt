package com.azizutku.movie.feature.movie.di

import com.azizutku.movie.core.common.util.Mapper
import com.azizutku.movie.core.database.model.MovieEntity
import com.azizutku.movie.feature.movie.data.remote.dto.MovieDto
import com.azizutku.movie.feature.movie.data.repository.MovieRepositoryImpl
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieCacheDataSource
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieLocalDataSource
import com.azizutku.movie.feature.movie.data.repository.datasource.MovieRemoteDataSource
import com.azizutku.movie.feature.movie.data.repository.datasourceImpl.MovieCacheDataSourceImpl
import com.azizutku.movie.feature.movie.data.repository.datasourceImpl.MovieLocalDataSourceImpl
import com.azizutku.movie.feature.movie.data.repository.datasourceImpl.MovieRemoteDataSourceImpl
import com.azizutku.movie.feature.movie.domain.model.Movie
import com.azizutku.movie.feature.movie.domain.model.MovieLocalMapper
import com.azizutku.movie.feature.movie.domain.model.MovieRemoteToLocalMapper
import com.azizutku.movie.feature.movie.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface MovieModule {

    @ViewModelScoped
    @Binds
    fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @ViewModelScoped
    @Binds
    fun bindRemoteDataSource(impl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @ViewModelScoped
    @Binds
    fun bindLocalDataSource(impl: MovieLocalDataSourceImpl): MovieLocalDataSource

    @ViewModelScoped
    @Binds
    fun bindLocalMapper(impl: MovieLocalMapper): Mapper<MovieEntity, Movie>

    @ViewModelScoped
    @Binds
    fun bindRemoteToLocalMapper(impl: MovieRemoteToLocalMapper): Mapper<MovieDto, MovieEntity>
}

@Module
@InstallIn(SingletonComponent::class)
interface MovieSingletonModule {
    @Singleton
    @Binds
    fun bindCacheDataSource(impl: MovieCacheDataSourceImpl): MovieCacheDataSource
}
