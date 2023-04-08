package com.azizutku.movie.feature.trending.di

import com.azizutku.movie.feature.trending.presentation.adapters.TrendingMovieLoadStateAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Qualifier

@Module
@InstallIn(FragmentComponent::class)
interface TrendingAdapterModule {
    @FragmentScoped
    @Binds
    @HeaderLoadStateAdapter
    fun bindHeaderLoadStateAdapter(impl: TrendingMovieLoadStateAdapter): TrendingMovieLoadStateAdapter

    @FragmentScoped
    @Binds
    @FooterLoadStateAdapter
    fun bindFooterLoadStateAdapter(impl: TrendingMovieLoadStateAdapter): TrendingMovieLoadStateAdapter
}

@Qualifier
annotation class HeaderLoadStateAdapter

@Qualifier
annotation class FooterLoadStateAdapter
