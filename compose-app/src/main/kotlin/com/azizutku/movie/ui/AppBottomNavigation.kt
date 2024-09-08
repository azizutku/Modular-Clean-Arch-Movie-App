package com.azizutku.movie.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import com.azizutku.movie.AppState
import com.azizutku.movie.core.common.extensions.orFalse
import com.azizutku.movie.navigation.TopLevelDestination
import com.azizutku.movie.rememberAppState
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.AppTypography
import com.azizutku.movie.ui.theme.appColors
import kotlin.reflect.KClass

@Composable
fun AppBottomNavigation(
    currentDestination: NavDestination?,
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(modifier = modifier) {
        TopLevelDestination.entries.forEach { destination ->
            val selected = currentDestination?.isRouteInHierarchy(destination.route).orFalse()
            HomeNavigationItem(destination = destination, selected = selected) {
                appState.navigateToTopLevelDestination(destination)
            }
        }
    }
}

@Composable
private fun RowScope.HomeNavigationItem(
    destination: TopLevelDestination,
    selected: Boolean,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        selected = selected,
        icon = {
            Icon(
                imageVector = destination.icon,
                contentDescription = stringResource(destination.iconContentDescriptionId),
            )
        },
        label = {
            Text(
                text = stringResource(destination.titleTextId),
                style = AppTypography.caption,
            )
        },
        selectedContentColor = MaterialTheme.appColors.bottomNavigationItemActive,
        unselectedContentColor = MaterialTheme.appColors.bottomNavigationItemInactive,
        alwaysShowLabel = false,
        onClick = onClick,
    )
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this?.hierarchy?.any {
        it.hasRoute(route)
    }.orFalse()
}

@Preview
@Composable
private fun AppBottomNavigationPreview() {
    AppTheme {
        AppBottomNavigation(
            currentDestination = rememberNavController().currentBackStackEntry?.destination,
            appState = rememberAppState()
        )
    }
}
