package com.azizutku.movie.common.di

import android.content.Context
import androidx.room.Room
import com.azizutku.movie.common.data.MainDatabase
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
    replaces = [DatabaseModule::class],
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
