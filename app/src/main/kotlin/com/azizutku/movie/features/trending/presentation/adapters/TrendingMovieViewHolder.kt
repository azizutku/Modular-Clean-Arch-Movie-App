package com.azizutku.movie.features.trending.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.azizutku.movie.common.base.BaseViewHolder
import com.azizutku.movie.databinding.ListItemTrendingMovieBinding
import com.azizutku.movie.features.trending.domain.model.TrendingMovie
import com.bumptech.glide.Glide

class TrendingMovieViewHolder(
    private val parent: ViewGroup,
    private val onItemClicked: ((id: Int) -> Unit)?,
) : BaseViewHolder<TrendingMovie, ListItemTrendingMovieBinding>(
    binding = ListItemTrendingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(item: TrendingMovie) {
        with(binding) {
            listItemTrendingMovieTextviewTitle.text = item.title
            listItemTrendingMovieTextviewReleaseDate.text = item.releaseDate
            listItemTrendingMovieTextviewVote.text = item.rating
            Glide.with(parent.context).load(item.posterUrl).centerCrop().into(listItemTrendingMovieImageviewPoster)
            binding.root.setOnClickListener {
                onItemClicked?.invoke(item.id)
            }
        }
    }
}
