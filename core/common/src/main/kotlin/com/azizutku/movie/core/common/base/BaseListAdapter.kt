package com.azizutku.movie.core.common.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    override fun submitList(list: List<T>?) {
        super.submitList(list?.toList())
    }
}
