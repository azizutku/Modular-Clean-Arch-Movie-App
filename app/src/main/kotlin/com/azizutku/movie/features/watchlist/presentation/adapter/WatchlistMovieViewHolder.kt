package com.azizutku.movie.features.watchlist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.azizutku.movie.core.common.base.BaseViewHolder
import com.azizutku.movie.databinding.ListItemWatchlistMovieBinding
import com.azizutku.movie.features.watchlist.domain.model.WatchlistMovie
import com.bumptech.glide.Glide

class WatchlistMovieViewHolder(
    private val parent: ViewGroup,
    private val onItemClicked: ((id: Int) -> Unit)?,
) : BaseViewHolder<WatchlistMovie, ListItemWatchlistMovieBinding>(
    binding = ListItemWatchlistMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(item: WatchlistMovie) {
        with(binding) {
            Glide.with(parent.context).load(item.posterUrl).centerCrop().into(listItemMovieWatchlistImageviewPoster)
            binding.root.setOnClickListener {
                onItemClicked?.invoke(item.id)
            }
        }
    }
}
