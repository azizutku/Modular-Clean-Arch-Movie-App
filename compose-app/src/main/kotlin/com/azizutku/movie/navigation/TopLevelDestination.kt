package com.azizutku.movie.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.azizutku.movie.R
import com.azizutku.movie.trending.presentation.navigation.TrendingRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon: ImageVector,
    @StringRes val titleTextId: Int,
    @StringRes val iconContentDescriptionId: Int,
    val route: KClass<*>,
) {
    TRENDING(
        icon = Icons.Rounded.Home,
        titleTextId = R.string.title_nav_item_trending,
        iconContentDescriptionId = R.string.content_description_nav_item_trending,
        route = TrendingRoute::class,
    ),
    WATCHLIST(
        icon = Icons.Rounded.Favorite,
        titleTextId = R.string.title_nav_item_watchlist,
        iconContentDescriptionId = R.string.content_description_nav_item_watchlist,
        route = WatchlistRoute::class,
    ),
}
