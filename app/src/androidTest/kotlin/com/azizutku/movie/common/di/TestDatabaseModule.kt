package com.azizutku.movie.common.di

import android.content.Context
import androidx.room.Room
import com.azizutku.movie.core.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [com.azizutku.movie.core.database.di.DatabaseModule::class],
)
object TestDatabaseModule {

    @Singleton
    @Provides
    fun provideMainDataBase(@ApplicationContext context: Context): MainDatabase =
        Room.inMemoryDatabaseBuilder(context, MainDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .build()
}
