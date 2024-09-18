package com.azizutku.movie.trending.presentation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovie

class TrendingMoviePagingDataPreviewParameterProvider : PreviewParameterProvider<PagingData<TrendingMovie>> {
    override val values: Sequence<PagingData<TrendingMovie>> = sequenceOf(
        PagingData.from(
            listOf(
                TrendingMovie(
                    id = 840705,
                    description = "When tech billionaire Slater King meets cocktail waitress Frida",
                    posterUrl = "https://image.tmdb.org/t/p/w500/lZGOK0I2DJSRlEPNOAFTSNxSjDD.jpg",
                    releaseDate = "21 Aug 2024",
                    title = "Blink Twice",
                    rating = "TMDB 6.8",
                ),
                TrendingMovie(
                    id = 1062215,
                    description = "Curtis Pike and his family are selected to test a new home device",
                    posterUrl = "https://image.tmdb.org/t/p/w500/gUREuXCnJLVHsvKXDH9fgIcfM6e.jpg",
                    releaseDate = "28 Aug 2024",
                    title = "Afraid",
                    rating = "TMDB 5.1",
                ),
                TrendingMovie(
                    id = 748167,
                    description = "In a futuristic dystopia with enforced beauty standards",
                    posterUrl = "https://image.tmdb.org/t/p/w500/jaUu9zHtbcFwrB5Y1DNYE09HMex.jpg",
                    releaseDate = "12 Sep 2024",
                    title = "Uglies",
                    rating = "TMDB 6.1",
                ),
                TrendingMovie(
                    id = 957452,
                    description = "Soulmates Eric and Shelly are brutally murdered when the demons of her dark past",
                    posterUrl = "https://image.tmdb.org/t/p/w500/58QT4cPJ2u2TqWZkterDq9q4yxQ.jpg",
                    releaseDate = "21 Aug 2024",
                    title = "The Crow",
                    rating = "TMDB 5.4",
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
