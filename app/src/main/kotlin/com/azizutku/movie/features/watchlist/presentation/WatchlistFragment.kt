package com.azizutku.movie.features.watchlist.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.azizutku.movie.R
import com.azizutku.movie.common.base.BaseFragment
import com.azizutku.movie.common.extensions.collectLatestLifecycleFlow
import com.azizutku.movie.common.ui.recyclerview.SpacingItemDecoration
import com.azizutku.movie.common.util.ThemeUtils
import com.azizutku.movie.databinding.FragmentWatchlistBinding
import com.azizutku.movie.features.watchlist.presentation.adapter.WatchlistMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>(
    inflate = FragmentWatchlistBinding::inflate
) {

    override val viewModel: WatchlistViewModel by viewModels()

    @Inject
    lateinit var themeUtils: ThemeUtils

    @Inject
    lateinit var adapter: WatchlistMoviesAdapter

    override fun initUi() {
        initToolbar()
        initRecyclerView()
        subscribeObservers()
        viewModel.getMoviesFromWatchlist()
    }

    private fun initRecyclerView() {
        adapter.onItemClicked = ::openMovieDetail
        binding.fragmentWatchlistRecyclerviewMovies.addItemDecoration(
            SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.space_medium), true)
        )
        binding.fragmentWatchlistRecyclerviewMovies.adapter = adapter
    }

    private fun subscribeObservers() {
        collectLatestLifecycleFlow(viewModel.uiState) { watchlistUiState ->
            if (watchlistUiState is WatchlistUiState.Success) {
                adapter.submitData(watchlistUiState.pagingData)
            }
        }
    }

    private fun initToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.watchlistFragment, R.id.movieFragment)
        )

        with(binding.fragmentWatchlistToolbar.toolbar) {
            inflateMenu(R.menu.toolbar_main_menu)
            title = getString(R.string.title_fragment_watchlist)
            setupWithNavController(navController, appBarConfiguration)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.item_toolbar_toggle_theme) {
                    toggleTheme()
                    return@setOnMenuItemClickListener true
                }
                false
            }
            setOnClickListener {
                binding.fragmentWatchlistRecyclerviewMovies.smoothScrollToPosition(0)
            }
        }
    }

    private fun toggleTheme() {
        themeUtils.toggleTheme(requireContext())
    }

    private fun openMovieDetail(movieId: Int) {
        findNavController().navigate(
            WatchlistFragmentDirections.actionWatchlistFragmentToMovieFragment(movieId)
        )
    }
}
