package com.azizutku.movie.core.common.di

import com.azizutku.movie.core.common.data.repository.UserDataRepository
import com.azizutku.movie.core.common.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserDataModule {

    @Singleton
    @Binds
    fun bindUserDataRepository(impl: UserDataRepositoryImpl): UserDataRepository
}
