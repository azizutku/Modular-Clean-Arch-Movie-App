package com.azizutku.movie.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.azizutku.movie.common.extensions.collectLatestLifecycleFlow
import com.azizutku.movie.common.ui.dialogs.AlertDialog
import com.azizutku.movie.common.ui.dialogs.LoadingDialog
import com.azizutku.movie.common.util.ErrorHandler
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
        subscribeLoading()
        subscribeError()
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeLoading() {
        collectLatestLifecycleFlow(viewModel.stateLoading) { isLoading ->
            if (isLoading) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        }
    }

    private fun subscribeError() {
        collectLatestLifecycleFlow(viewModel.stateError) {
            errorHandler.handleException(it.exception)
        }
    }
}
