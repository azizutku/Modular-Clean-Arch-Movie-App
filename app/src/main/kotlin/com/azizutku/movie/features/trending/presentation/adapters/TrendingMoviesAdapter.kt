package com.azizutku.movie.features.trending.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.azizutku.movie.features.trending.domain.model.TrendingMovie
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class TrendingMoviesAdapter @Inject constructor() :
    PagingDataAdapter<TrendingMovie, TrendingMovieViewHolder>(DiffCallback) {

    var onItemClicked: ((id: Int) -> Unit)? = null

    object DiffCallback : DiffUtil.ItemCallback<TrendingMovie>() {
        override fun areItemsTheSame(old: TrendingMovie, new: TrendingMovie): Boolean = old.id == new.id
        override fun areContentsTheSame(old: TrendingMovie, new: TrendingMovie): Boolean = old == new
    }

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder =
        TrendingMovieViewHolder(parent, onItemClicked)
}
