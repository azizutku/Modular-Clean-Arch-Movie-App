package com.azizutku.movie.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.azizutku.movie.AppState
import com.azizutku.movie.feature.movie.navigation.movieScreen
import com.azizutku.movie.feature.watchlist.navigation.watchlistScreen
import com.azizutku.movie.trending.presentation.navigation.TrendingRoute
import com.azizutku.movie.trending.presentation.navigation.trendingScreen

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = TrendingRoute,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        modifier = modifier,
    ) {
        trendingScreen(navController)
        watchlistScreen(navController)
        movieScreen(navController)
    }
}
