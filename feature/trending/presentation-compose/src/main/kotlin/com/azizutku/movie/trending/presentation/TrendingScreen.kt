package com.azizutku.movie.trending.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.feature.trending.R
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovie
import com.azizutku.movie.feature.trending.common.presentation.TrendingViewModel
import com.azizutku.movie.ui.NavigationAction
import com.azizutku.movie.ui.components.MovieAppTopAppBar
import com.azizutku.movie.ui.components.TopAppBarAction
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.AppTypography
import com.azizutku.movie.ui.theme.PreviewTheme
import kotlinx.coroutines.flow.flowOf
import com.azizutku.feature.trending.common.R as trendingCommonR
import com.azizutku.movie.core.common.R as coreCommonR
import com.azizutku.movie.core.ui.common.R as uiCommonR
import com.azizutku.movie.core.ui.compose.R as uiComposeR

private const val ImageWidthPercent = 0.40f
private const val ImageAspectRatio = 1f / 1.5f
private val RatingBackgroundColor = Color(color = 0xFF09B4E4)

@Composable
internal fun TrendingScreen(
    onNavigationAction: (NavigationAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TrendingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lazyPagingItems = viewModel.pagingState.collectAsLazyPagingItems()
    TrendingScreen(
        lazyPagingItems = lazyPagingItems,
        modifier = modifier,
        onToggleTheme = {
            viewModel.toggleTheme(context)
        },
        onNavigationAction = onNavigationAction,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TrendingScreen(
    lazyPagingItems: LazyPagingItems<TrendingMovie>,
    modifier: Modifier = Modifier,
    onToggleTheme: () -> Unit = {},
    onNavigationAction: (NavigationAction) -> Unit = {},
) {
    val loadState = lazyPagingItems.loadState
    val pullRefreshState = rememberPullRefreshState(
        refreshing = loadState.refresh is LoadState.Loading,
        onRefresh = lazyPagingItems::refresh,
    )
    Column(modifier = modifier) {
        MovieAppTopAppBar(
            title = stringResource(R.string.title_trending_screen),
            hideNavigationIcon = true,
            actions = listOf(
                TopAppBarAction(
                    imageVector = ImageVector.vectorResource(uiComposeR.drawable.ic_toggle_theme_24),
                    contentDescription = stringResource(
                        uiComposeR.string.content_description_app_bar_action_toggle_theme
                    ),
                ) {
                    onToggleTheme()
                }
            ),
            onNavigationIconClick = {
                onNavigationAction(NavigationAction.OnBackButtonClicked)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
        ) {
            TrendingList(lazyPagingItems, loadState, onNavigationAction)
            PullRefreshIndicator(
                refreshing = loadState.refresh is LoadState.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
private fun TrendingList(
    lazyPagingItems: LazyPagingItems<TrendingMovie>,
    loadState: CombinedLoadStates,
    onNavigationAction: (NavigationAction) -> Unit = {},
) {
    val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        item { /* Empty Item */ }
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
        when {
            loadState.append is LoadState.Loading && lazyPagingItems.itemCount != 0 -> {
                item {
                    ListLoadingItem()
                }
            }

            loadState.append is LoadState.Error -> {
                item {
                    ListErrorItem(loadState, lazyPagingItems)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListItem(item: TrendingMovie, onClick: () -> Unit) {
    val rowAspectRatio = ImageAspectRatio / ImageWidthPercent
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(rowAspectRatio),
        ) {
            AsyncImage(
                model = item.posterUrl,
                contentDescription = stringResource(
                    trendingCommonR.string.content_description_trending_movie_poster_image,
                ),
                placeholder = painterResource(id = uiCommonR.drawable.bg_placeholder_movie_image),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(ImageWidthPercent)
                    .aspectRatio(ImageAspectRatio),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = item.title,
                    style = AppTypography.h6,
                    textAlign = TextAlign.Center,
                )
                RatingBadge(item.rating)
                Text(
                    text = item.releaseDate,
                    style = AppTypography.body1,
                )
            }
        }
    }
}

@Composable
private fun ListErrorItem(
    loadState: CombinedLoadStates,
    lazyPagingItems: LazyPagingItems<TrendingMovie>,
) {
    Column {
        val message = ((loadState.append as? LoadState.Error)?.error as? NetworkException)?.message
        if (message != null) {
            Text(
                text = message,
                style = MaterialTheme.typography.body2,
            )
        }
        RetryButton {
            lazyPagingItems.refresh()
        }
    }
}

@Composable
private fun ListLoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun RatingBadge(rating: String) {
    Text(
        text = rating,
        style = AppTypography.subtitle2.copy(
            platformStyle = PlatformTextStyle(
                // Including font padding normally increases the text height,
                // but interestingly, setting it to true makes it shorter here.
                includeFontPadding = true,
            ),
        ),
        color = Color.White,
        modifier = Modifier
            .background(
                color = RatingBackgroundColor,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(vertical = 4.dp, horizontal = 12.dp),
    )
}

@Composable
private fun RetryButton(onRetry: () -> Unit) {
    Button(
        onClick = onRetry,
        modifier = Modifier,
    ) {
        Icon(
            painter = painterResource(id = trendingCommonR.drawable.ic_line_refresh_24),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Try again",
            style = MaterialTheme.typography.button,
        )
    }
}

@Composable
@PreviewTheme
private fun TrendingScreenPreview(
    @PreviewParameter(TrendingMoviePagingDataPreviewParameterProvider::class)
    pagingData: PagingData<TrendingMovie>,
) {
    AppTheme {
        TrendingScreen(
            lazyPagingItems = flowOf(pagingData).collectAsLazyPagingItems(),
        )
    }
}

@Composable
@PreviewTheme
private fun RetryButtonPreview() {
    AppTheme {
        RetryButton { /* no-op */ }
    }
}

@Composable
@Preview
private fun RatingBadgePreview() {
    AppTheme {
        RatingBadge("TMDB 6.4")
    }
}
