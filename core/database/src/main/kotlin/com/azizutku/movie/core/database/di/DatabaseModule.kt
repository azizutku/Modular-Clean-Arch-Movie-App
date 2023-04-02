package com.azizutku.movie.core.database.di

import android.content.Context
import androidx.room.Room
import com.azizutku.movie.core.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "main_db"

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideMainDataBase(@ApplicationContext appContext: Context): MainDatabase =
        Room.databaseBuilder(appContext, MainDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

}
