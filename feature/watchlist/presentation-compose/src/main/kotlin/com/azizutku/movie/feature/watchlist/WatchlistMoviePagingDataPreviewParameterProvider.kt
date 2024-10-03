package com.azizutku.movie.feature.watchlist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.azizutku.movie.core.model.watchlist.WatchlistMovie

class WatchlistMoviePagingDataPreviewParameterProvider : PreviewParameterProvider<PagingData<WatchlistMovie>> {
    override val values: Sequence<PagingData<WatchlistMovie>> = sequenceOf(
        PagingData.from(
            listOf(
                WatchlistMovie(
                    id = 840705,
                    posterUrl = "https://image.tmdb.org/t/p/w500/lZGOK0I2DJSRlEPNOAFTSNxSjDD.jpg",
                ),
                WatchlistMovie(
                    id = 1062215,
                    posterUrl = "https://image.tmdb.org/t/p/w500/gUREuXCnJLVHsvKXDH9fgIcfM6e.jpg",
                ),
                WatchlistMovie(
                    id = 748167,
                    posterUrl = "https://image.tmdb.org/t/p/w500/jaUu9zHtbcFwrB5Y1DNYE09HMex.jpg",
                ),
                WatchlistMovie(
                    id = 957452,
                    posterUrl = "https://image.tmdb.org/t/p/w500/58QT4cPJ2u2TqWZkterDq9q4yxQ.jpg",
                ),
                WatchlistMovie(
                    id = 443413,
                    posterUrl = "https://image.tmdb.org/t/p/w500/58QT4cPJ2u2TqWZkterDq9q4yxQ.jpg",
                ),
            ),
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
            ),
        ),
    )
}
