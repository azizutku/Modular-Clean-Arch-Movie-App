package com.azizutku.movie.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.azizutku.movie.core.common.base.BaseViewModel
import com.azizutku.movie.core.common.base.ErrorOwner
import com.azizutku.movie.core.common.base.LoadingOwner
import com.azizutku.movie.core.common.extensions.collectLatestLifecycleFlow
import com.azizutku.movie.core.ui.dialogs.AlertDialog
import com.azizutku.movie.core.ui.dialogs.LoadingDialog
import com.azizutku.movie.core.common.util.ErrorHandler
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    protected abstract val viewModel: BaseViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var loadingDialog: LoadingDialog

    @Inject
    lateinit var alertDialog: AlertDialog

    private var _binding: VB? = null
    val binding: VB
        get() = _binding!!

    protected abstract fun initUi()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLoadingIfNeeded()
        subscribeErrorIfNeeded()
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeLoadingIfNeeded() {
        val loadingOwner = viewModel as? LoadingOwner ?: return
        collectLatestLifecycleFlow(loadingOwner.stateLoading) { isLoading ->
            if (isLoading) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        }
    }

    private fun subscribeErrorIfNeeded() {
        val errorOwner = viewModel as? ErrorOwner ?: return
        collectLatestLifecycleFlow(errorOwner.stateError) {
            errorHandler.handleException(it.exception)
        }
    }
}
