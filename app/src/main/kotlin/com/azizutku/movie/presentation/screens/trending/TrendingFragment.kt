package com.azizutku.movie.presentation.screens.trending

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.azizutku.movie.R
import com.azizutku.movie.databinding.FragmentTrendingBinding
import com.azizutku.movie.presentation.base.BaseFragment
import com.azizutku.movie.util.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendingFragment : BaseFragment<FragmentTrendingBinding>(
    inflate = FragmentTrendingBinding::inflate
) {

    override val viewModel: TrendingViewModel by viewModels()

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun initUi() {
        initToolbar()
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        // TODO: Initialize recycler view
    }

    private fun subscribeObservers() {
        // TODO: Subscribe observers
    }

    private fun initToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.trendingFragment, R.id.movieFragment)
        )

        with(binding.fragmentTrendingToolbar.toolbar) {
            inflateMenu(R.menu.toolbar_main_menu)
            title = getString(R.string.title_fragment_trending)
            setupWithNavController(navController, appBarConfiguration)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.item_toolbar_toggle_theme) {
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
}
