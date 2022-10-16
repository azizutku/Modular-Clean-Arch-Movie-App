package com.azizutku.movie.presentation.screens.movie

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.azizutku.movie.R
import com.azizutku.movie.databinding.FragmentMovieBinding
import com.azizutku.movie.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(
    inflate = FragmentMovieBinding::inflate
) {

    override val viewModel: MovieViewModel by viewModels()

    private val args: MovieFragmentArgs by navArgs()

    private val movieId by lazy { args.movieId }

    override fun initUi() {
        initToolbar()
        initErrorHandler()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        // TODO: Subscribe observers
    }

    private fun onMenuItemWatchlistAction() {
        // TODO: Add movie to watchlist or remove from watchlist
    }

    private fun initToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        with(binding.fragmentMovieToolbar.toolbar) {
            inflateMenu(R.menu.toolbar_movie_menu)
            setupWithNavController(navController, appBarConfiguration)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.item_toolbar_watchlist_action) {
                    onMenuItemWatchlistAction()
                    return@setOnMenuItemClickListener true
                }
                false
            }
        }
    }

    private fun initErrorHandler() {
        errorHandler.onDefaultPrimaryAction = {
            findNavController().popBackStack()
        }
        errorHandler.defaultTextPrimaryAction = requireContext().getString(R.string.text_button_go_back)
    }
}
