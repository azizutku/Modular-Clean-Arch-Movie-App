package com.azizutku.movie.trending.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.azizutku.movie.core.common.network.NetworkException
import com.azizutku.movie.feature.trending.common.domain.model.TrendingMovie
import com.azizutku.movie.feature.trending.common.presentation.TrendingViewModel
import com.azizutku.movie.ui.theme.AppTheme
import com.azizutku.movie.ui.theme.PreviewTheme
import com.azizutku.feature.trending.common.R as trendingCommonR

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrendingScreen(
    modifier: Modifier = Modifier,
    viewModel: TrendingViewModel = hiltViewModel(),
) {
    val lazyPagingItems = viewModel.pagingState.collectAsLazyPagingItems()
    val loadState = lazyPagingItems.loadState
    val pullRefreshState = rememberPullRefreshState(
        refreshing = loadState.refresh is LoadState.Loading,
        onRefresh = {
            lazyPagingItems.refresh()
        },
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        TrendingList(lazyPagingItems, loadState)
        PullRefreshIndicator(
            refreshing = loadState.refresh is LoadState.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun TrendingList(
    lazyPagingItems: LazyPagingItems<TrendingMovie>,
    loadState: CombinedLoadStates,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            lazyPagingItems.itemCount,
            lazyPagingItems.itemKey {
                it.id
            },
        ) { index ->
            val item = lazyPagingItems[index] ?: return@items
            ListItem(item)
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
    }
}

@Composable
private fun ListItem(item: TrendingMovie) {
    Text(
        modifier = Modifier
            .height(120.dp)
            .background(Color.Red),
        text = item.title,
        textAlign = TextAlign.Center,
    )
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
        )
    }
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
private fun RetryButtonPreview() {
    AppTheme {
        RetryButton {
            // no-op
        }
    }
}
