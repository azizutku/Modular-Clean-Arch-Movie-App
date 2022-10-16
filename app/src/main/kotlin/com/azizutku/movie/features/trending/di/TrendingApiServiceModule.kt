package com.azizutku.movie.features.trending.di

import com.azizutku.movie.features.trending.data.remote.TrendingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object TrendingApiServiceModule {

    @ViewModelScoped
    @Provides
    fun provideTrendingApiService(retrofit: Retrofit): TrendingApiService = retrofit.create(
        TrendingApiService::class.java
    )
}
