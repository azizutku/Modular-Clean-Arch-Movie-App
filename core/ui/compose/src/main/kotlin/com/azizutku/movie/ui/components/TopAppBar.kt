@file:Suppress("UsingMaterialAndMaterial3Libraries")

package com.azizutku.movie.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.AppTypography
import com.azizutku.movie.ui.theme.PreviewTheme

@Composable
fun MovieAppTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    hideNavigationIcon: Boolean = false,
    actions: List<TopAppBarAction> = emptyList(),
    onNavigationIconClick: () -> Unit = { },
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = title,
                style = AppTypography.h6,
                color = MaterialTheme.colors.onSurface,
            )
        },
        navigationIcon = {
            if (hideNavigationIcon.not()) {
                IconButton(
                    onClick = onNavigationIconClick,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
            }
        },
        actions = {
            actions.forEach { action ->
                IconButton(
                    enabled = action.enabled,
                    onClick = action.onClick,
                ) {
                    Icon(
                        imageVector = action.imageVector,
                        contentDescription = action.contentDescription,
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
            }
        },
    )
}

/*
 * As a workaround for the lack of a Center Aligned Top App Bar in Material2,
 * this implementation leverages the Material3 CenterAlignedTopAppBar component.
 * This allows for the center-aligned title feature while maintaining backward
 * compatibility with existing Material2 themes.
 */
@Composable
private fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    @OptIn(ExperimentalMaterial3Api::class)
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = androidx.compose.material3.TopAppBarColors(
            containerColor = colors.containerColor,
            scrolledContainerColor = colors.scrolledContainerColor,
            navigationIconContentColor = colors.navigationIconContentColor,
            titleContentColor = colors.titleContentColor,
            actionIconContentColor = colors.actionIconContentColor,
        ),
    )
}

@Composable
@PreviewTheme
private fun MovieAppTopAppBarPreview() {
    AppTheme {
        MovieAppTopAppBar(title = "Trending Screen")
    }
}

object TopAppBarDefaults {
    @Composable
    fun topAppBarColors() = TopAppBarColors(
        containerColor = MaterialTheme.colors.background,
        scrolledContainerColor = MaterialTheme.colors.onSurface,
        navigationIconContentColor = MaterialTheme.colors.onSurface,
        titleContentColor = MaterialTheme.colors.onSurface,
        actionIconContentColor = MaterialTheme.colors.onSurface,
    )
}

class TopAppBarColors(
    val containerColor: Color,
    val scrolledContainerColor: Color,
    val navigationIconContentColor: Color,
    val titleContentColor: Color,
    val actionIconContentColor: Color,
)

class TopAppBarAction(
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val enabled: Boolean = true,
    val onClick: () -> Unit = { },
)
