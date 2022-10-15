package com.azizutku.movie.di

import com.azizutku.movie.util.ErrorHandler
import com.azizutku.movie.util.ErrorHandlerImpl
import com.azizutku.movie.util.ThemeUtils
import com.azizutku.movie.util.ThemeUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun bindThemeUtils(impl: ThemeUtilsImpl): ThemeUtils
}

@Module
@InstallIn(FragmentComponent::class)
abstract class UtilsFragmentModule {

    @FragmentScoped
    @Binds
    abstract fun bindErrorHandler(impl: ErrorHandlerImpl): ErrorHandler
}
