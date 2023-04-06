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
abstract class TrendingAdapterModule {
    @FragmentScoped
    @Binds
    @HeaderLoadStateAdapter
    abstract fun bindHeaderLoadStateAdapter(impl: TrendingMovieLoadStateAdapter): TrendingMovieLoadStateAdapter

    @FragmentScoped
    @Binds
    @FooterLoadStateAdapter
    abstract fun bindFooterLoadStateAdapter(impl: TrendingMovieLoadStateAdapter): TrendingMovieLoadStateAdapter
}

@Qualifier
annotation class HeaderLoadStateAdapter

@Qualifier
annotation class FooterLoadStateAdapter
