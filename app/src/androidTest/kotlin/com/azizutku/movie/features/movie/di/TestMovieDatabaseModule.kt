package com.azizutku.movie.features.movie.di

import com.azizutku.movie.common.data.MainDatabase
import com.azizutku.movie.features.movie.data.local.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MovieDatabaseModule::class],
)
object TestMovieDatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDao(database: MainDatabase): MoviesDao = database.moviesDao()
}
