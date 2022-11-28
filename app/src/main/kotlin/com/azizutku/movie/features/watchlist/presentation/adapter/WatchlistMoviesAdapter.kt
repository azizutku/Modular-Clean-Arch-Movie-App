package com.azizutku.movie.features.watchlist.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class WatchlistMoviesAdapter @Inject constructor() :
    PagingDataAdapter<WatchlistMovie, WatchlistMovieViewHolder>(DiffCallback) {

    var onItemClicked: ((id: Int) -> Unit)? = null

    object DiffCallback : DiffUtil.ItemCallback<WatchlistMovie>() {
        override fun areItemsTheSame(old: WatchlistMovie, new: WatchlistMovie): Boolean = old.id == new.id
        override fun areContentsTheSame(old: WatchlistMovie, new: WatchlistMovie): Boolean = old == new
    }

    override fun onBindViewHolder(holder: WatchlistMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistMovieViewHolder =
        WatchlistMovieViewHolder(parent, onItemClicked)
}
