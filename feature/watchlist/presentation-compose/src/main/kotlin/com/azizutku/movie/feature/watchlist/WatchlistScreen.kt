package com.azizutku.movie.feature.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.azizutku.movie.core.model.watchlist.WatchlistMovie
import com.azizutku.movie.feature.watchlist.common.presentation.WatchlistViewModel
import com.azizutku.movie.ui.NavigationAction
import com.azizutku.movie.ui.components.MovieAppTopAppBar
import com.azizutku.movie.ui.components.TopAppBarAction
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.PreviewTheme
import kotlinx.coroutines.flow.flowOf
import com.azizutku.movie.core.common.R as coreCommonR
import com.azizutku.movie.core.ui.common.R as coreUiCommonR
import com.azizutku.movie.core.ui.compose.R as coreUiComposeR
import com.azizutku.movie.feature.watchlist.common.R as watchlistCommonR

private const val ImageAspectRatio = 1f / 1.5f

@Composable
fun WatchlistScreen(
    onNavigationAction: (NavigationAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WatchlistViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lazyPagingItems = viewModel.pagingState.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        viewModel.getMoviesFromWatchlist()
    }
    WatchlistScreen(
        lazyPagingItems = lazyPagingItems,
        modifier = modifier,
        onToggleTheme = {
            viewModel.toggleTheme(context)
        },
        onNavigationAction = onNavigationAction,
    )
}

@Composable
internal fun WatchlistScreen(
    lazyPagingItems: LazyPagingItems<WatchlistMovie>,
    modifier: Modifier = Modifier,
    onToggleTheme: () -> Unit = {},
    onNavigationAction: (NavigationAction) -> Unit = {},
) {
    Column(modifier = modifier) {
        MovieAppTopAppBar(
            title = stringResource(R.string.title_watchlist_screen),
            hideNavigationIcon = true,
            actions = listOf(
                TopAppBarAction(
                    imageVector = ImageVector.vectorResource(coreUiComposeR.drawable.ic_toggle_theme_24),
                    contentDescription = stringResource(
                        coreUiComposeR.string.content_description_app_bar_action_toggle_theme,
                    ),
                ) {
                    onToggleTheme()
                },
            ),
        )
        WatchList(lazyPagingItems, onNavigationAction)
    }
}

@Composable
private fun WatchList(
    lazyPagingItems: LazyPagingItems<WatchlistMovie>,
    onNavigationAction: (NavigationAction) -> Unit = {},
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) { /* Empty Item */ }
        items(
            lazyPagingItems.itemCount,
            lazyPagingItems.itemKey {
                it.id
            },
        ) { index ->
            val item = lazyPagingItems[index] ?: return@items
            ListItem(item) {
                val deeplink = context.getString(coreCommonR.string.deep_link_movie).replace(
                    oldValue = "{movieId}",
                    newValue = item.id.toString(),
                )
                onNavigationAction(NavigationAction.NavigateTo(deeplink))
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) { /* Empty Item */ }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListItem(item: WatchlistMovie, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        AsyncImage(
            model = item.posterUrl,
            contentDescription = stringResource(
                watchlistCommonR.string.content_description_movie_image,
            ),
            placeholder = painterResource(id = coreUiCommonR.drawable.bg_placeholder_movie_image),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ImageAspectRatio),
        )
    }
}

@Composable
@PreviewTheme
private fun TrendingScreenPreview(
    @PreviewParameter(WatchlistMoviePagingDataPreviewParameterProvider::class)
    pagingData: PagingData<WatchlistMovie>,
) {
    AppTheme {
        WatchlistScreen(
            lazyPagingItems = flowOf(pagingData).collectAsLazyPagingItems(),
        )
    }
}
