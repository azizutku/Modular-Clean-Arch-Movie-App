package com.azizutku.movie.feature.trending.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import com.azizutku.movie.core.common.extensions.collectLatestLifecycleFlow
import com.azizutku.movie.core.common.extensions.navigateToMovie
import com.azizutku.movie.core.common.util.ThemeUtils
import com.azizutku.movie.core.ui.base.BaseFragment
import com.azizutku.movie.core.ui.recyclerview.SpacingItemDecoration
import com.azizutku.movie.feature.trending.R
import com.azizutku.movie.feature.trending.databinding.FragmentTrendingBinding
import com.azizutku.movie.feature.trending.di.FooterLoadStateAdapter
import com.azizutku.movie.feature.trending.di.HeaderLoadStateAdapter
import com.azizutku.movie.feature.trending.presentation.adapters.TrendingMovieLoadStateAdapter
import com.azizutku.movie.feature.trending.presentation.adapters.TrendingMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.azizutku.movie.core.ui.R as uiR

@AndroidEntryPoint
class TrendingFragment : BaseFragment<FragmentTrendingBinding>(
    inflate = FragmentTrendingBinding::inflate
) {

    override val viewModel: TrendingViewModel by viewModels()

    @Inject
    lateinit var themeUtils: ThemeUtils

    @Inject
    lateinit var adapter: TrendingMoviesAdapter

    @Inject
    @HeaderLoadStateAdapter
    lateinit var headerLoadStateAdapter: TrendingMovieLoadStateAdapter

    @Inject
    @FooterLoadStateAdapter
    lateinit var footerLoadStateAdapter: TrendingMovieLoadStateAdapter

    override fun initUi() {
        initToolbar()
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        adapter.onItemClicked = ::openMovieDetail
        binding.fragmentTrendingSwipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
        binding.fragmentTrendingRecyclerviewTrending.addItemDecoration(
            SpacingItemDecoration(resources.getDimensionPixelSize(uiR.dimen.space_medium), true)
        )
        binding.fragmentTrendingRecyclerviewTrending.adapter = adapter.withLoadStateHeaderAndFooter(
            header = headerLoadStateAdapter,
            footer = footerLoadStateAdapter,
        )
    }

    private fun subscribeObservers() {
        collectLatestLifecycleFlow(viewModel.uiState) { launchesUiState ->
            if (launchesUiState is TrendingUiState.Success) {
                adapter.submitData(launchesUiState.pagingData)
            }
        }
        collectLatestLifecycleFlow(adapter.loadStateFlow) {
            binding.fragmentTrendingSwipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }
    }

    private fun initToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.trendingFragment)
        )

        with(binding.fragmentTrendingToolbar.toolbar) {
            inflateMenu(uiR.menu.toolbar_main_menu)
            title = getString(R.string.title_fragment_trending)
            setupWithNavController(navController, appBarConfiguration)
            setOnMenuItemClickListener {
                if (it.itemId == uiR.id.item_toolbar_toggle_theme) {
                    toggleTheme()
                    return@setOnMenuItemClickListener true
                }
                false
            }
            setOnClickListener {
                binding.fragmentTrendingRecyclerviewTrending.smoothScrollToPosition(0)
            }
        }
    }

    private fun toggleTheme() {
        themeUtils.toggleTheme(requireContext())
    }

    private fun openMovieDetail(movieId: Int) {
        findNavController().navigateToMovie(movieId)
    }
}
