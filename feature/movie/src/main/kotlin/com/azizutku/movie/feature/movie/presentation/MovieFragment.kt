package com.azizutku.movie.feature.movie.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.azizutku.movie.core.common.extensions.collectLatestLifecycleFlow
import com.azizutku.movie.core.common.extensions.orFalse
import com.azizutku.movie.core.ui.base.BaseFragment
import com.azizutku.movie.core.ui.extensions.setTextIfAvailableOrHide
import com.azizutku.movie.core.ui.extensions.setVisible
import com.azizutku.movie.feature.movie.R
import com.azizutku.movie.feature.movie.databinding.FragmentMovieBinding
import com.azizutku.movie.feature.movie.domain.model.Movie
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.azizutku.movie.core.ui.R as uiR

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(
    inflate = FragmentMovieBinding::inflate
) {

    override val viewModel: MovieViewModel by viewModels()

    private val args: MovieFragmentArgs by navArgs()

    private val movieId by lazy { args.movieId }

    private var isMovieInWatchlist = false

    override fun initUi() {
        initToolbar()
        initErrorHandler()
        subscribeObservers()
        viewModel.getMovie(movieId)
        viewModel.isInWatchlist(movieId)
    }

    private fun subscribeObservers() {
        collectLatestLifecycleFlow(viewModel.uiState) { uiState ->
            if (uiState is MovieUiState.Success) {
                uiState.movie?.let { movie ->
                    updateUi(movie)
                }
                handleMovieIsInWatchlistState(uiState.isMovieInWatchlist?.isInWatchlist.orFalse())
            }
        }
    }

    private fun handleMovieIsInWatchlistState(isInWatchlist: Boolean) {
        isMovieInWatchlist = isInWatchlist
        adjustMenuItemWatchlistAction()
    }

    private fun updateUi(movie: Movie) {
        with(binding) {
            fragmentMovieGroupContent.setVisible(true)
            fragmentMovieTextviewTitle.text = movie.title
            fragmentMovieTextviewSubtitle.text = movie.subtitle
            fragmentMovieTextviewTagline.setTextIfAvailableOrHide(
                text = movie.tagline,
                fragmentMovieViewDividerFirst,
            )
            fragmentMovieTextviewDetails.text = movie.description
            Glide.with(requireContext()).load(movie.posterUrl).centerCrop().into(fragmentMovieImageviewMovie)
        }
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
        errorHandler.defaultTextPrimaryAction = requireContext().getString(uiR.string.text_button_go_back)
    }

    private fun onMenuItemWatchlistAction() {
        if (isMovieInWatchlist.not()) {
            viewModel.addToWatchlist(movieId)
        } else {
            viewModel.removeFromWatchlist(movieId)
        }
    }

    private fun adjustMenuItemWatchlistAction() {
        val itemWatchlistAction = binding.fragmentMovieToolbar.toolbar.menu.findItem(R.id.item_toolbar_watchlist_action)
        itemWatchlistAction?.apply {
            if (isMovieInWatchlist) {
                title = requireContext().getString(R.string.title_toolbar_menu_item_watchlist_action_remove)
                setIcon(uiR.drawable.ic_fill_favorite_24)
            } else {
                title = requireContext().getString(R.string.title_toolbar_menu_item_watchlist_action_add)
                setIcon(R.drawable.ic_line_favorite_24)
            }
        }
    }
}
