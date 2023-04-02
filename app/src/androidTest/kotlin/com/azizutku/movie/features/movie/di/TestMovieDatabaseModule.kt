package com.azizutku.movie.features.movie.di

import com.azizutku.movie.core.database.MainDatabase
import com.azizutku.movie.core.database.dao.MoviesDao
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
