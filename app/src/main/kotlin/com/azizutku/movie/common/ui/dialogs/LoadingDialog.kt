package com.azizutku.movie.common.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.azizutku.movie.databinding.LayoutDialogLoadingBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LoadingDialog @Inject constructor(@ActivityContext context: Context) : Dialog(context) {
    private var binding: LayoutDialogLoadingBinding = LayoutDialogLoadingBinding.inflate(
        LayoutInflater.from(context),
        null,
        false
    )

    init {
        setContentView(binding.root)
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        setCancelable(false)
    }
}
