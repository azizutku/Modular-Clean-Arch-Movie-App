package com.azizutku.movie.core.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.azizutku.movie.core.ui.databinding.LayoutDialogLoadingBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LoadingDialog @Inject constructor(@ActivityContext context: Context) : Dialog(context) {
    private val binding: LayoutDialogLoadingBinding = LayoutDialogLoadingBinding.inflate(
        LayoutInflater.from(context),
        null,
        false
    )

    init {
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }
}
