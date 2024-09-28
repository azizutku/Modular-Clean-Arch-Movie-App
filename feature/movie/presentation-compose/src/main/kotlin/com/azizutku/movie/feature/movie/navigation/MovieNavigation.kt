@file:Suppress("MatchingDeclarationName")

package com.azizutku.movie.feature.movie.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.azizutku.movie.feature.movie.MovieScreen
import com.azizutku.movie.ui.handleNavigationAction
import kotlinx.serialization.Serializable

@Serializable
data class MovieRoute(val movieId: Int)

fun NavGraphBuilder.movieScreen(navController: NavHostController) {
    composable<MovieRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "movieapp://movie/{movieId}"
            },
        ),
    ) {
        MovieScreen(
            movieId = it.toRoute<MovieRoute>().movieId,
            onNavigationAction = navController::handleNavigationAction,
        )
    }
}
