package com.azizutku.movie.features.trending.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.azizutku.movie.common.base.BaseViewHolder
import com.azizutku.movie.common.network.NetworkException
import com.azizutku.movie.databinding.ListItemLoadStateBinding

class TrendingMovieLoadStateViewHolder(
    parent: ViewGroup,
    private val onRetry: () -> Unit
) : BaseViewHolder<LoadState, ListItemLoadStateBinding>(
    binding = ListItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(item: LoadState) {
        with(binding) {
            listItemLoadStateButtonTryAgain.setOnClickListener { onRetry() }
            if (item is LoadState.Loading) {
                listItemLoadStateProgressBarLoading.show()
            } else {
                listItemLoadStateProgressBarLoading.hide()
            }
            listItemLoadStateButtonTryAgain.isVisible = item is LoadState.Error
            listItemLoadStateTextViewMessage.text = ((item as? LoadState.Error)?.error as? NetworkException)?.message
            listItemLoadStateTextViewMessage.isVisible = item is LoadState.Error &&
                listItemLoadStateTextViewMessage.text.isNotEmpty()
        }
    }
}
