package com.azizutku.movie.features.trending.presentation.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import javax.inject.Inject

class TrendingMovieLoadStateAdapter @Inject constructor(
    private val adapter: TrendingMoviesAdapter
) : LoadStateAdapter<TrendingMovieLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: TrendingMovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): TrendingMovieLoadStateViewHolder =
        TrendingMovieLoadStateViewHolder(parent) {
            adapter.retry()
        }
}
