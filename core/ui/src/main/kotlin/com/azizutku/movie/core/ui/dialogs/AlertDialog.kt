package com.azizutku.movie.core.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.azizutku.movie.core.ui.extensions.setTextIfAvailableOrHide
import com.azizutku.movie.core.ui.extensions.setVisible
import com.azizutku.movie.core.ui.R
import com.azizutku.movie.core.ui.databinding.LayoutDialogAlertBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AlertDialog @Inject constructor(@ActivityContext context: Context) : Dialog(context, R.style.Theme_Dialog) {
    private var binding: LayoutDialogAlertBinding = LayoutDialogAlertBinding.inflate(
        LayoutInflater.from(context),
        null,
        false
    )

    init {
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        setContentView(binding.root)
    }

    fun setTitle(title: String?) = apply {
        binding.dialogAlertTextviewTitle.text = title
    }

    fun setMessage(message: String?) = apply {
        binding.dialogAlertTextviewMessage.setTextIfAvailableOrHide(message)
    }

    fun setHeaderImage(@DrawableRes resId: Int) = apply {
        binding.dialogAlertImageviewImage.setImageResource(resId)
    }

    fun setPositiveButton(
        text: CharSequence?,
        onClickListener: (dialog: AlertDialog) -> Unit,
    ) = apply {
        with(binding.dialogAlertTextviewPositiveAction) {
            this.text = text
            setOnClickListener {
                onClickListener(this@AlertDialog)
            }
        }
    }

    fun setNegativeButton(
        text: CharSequence?,
        onClickListener: (dialog: AlertDialog) -> Unit,
    ) = apply {
        with(binding.dialogAlertTextviewNegativeAction) {
            setTextIfAvailableOrHide(text.toString())
            setOnClickListener {
                onClickListener(this@AlertDialog)
            }
        }
    }

    fun setPositiveButton(text: CharSequence?) = apply {
        with(binding.dialogAlertTextviewPositiveAction) {
            this.text = text
            setOnClickListener { dismiss() }
        }
    }

    fun setNegativeButton(text: CharSequence?) = apply {
        with(binding.dialogAlertTextviewNegativeAction) {
            setTextIfAvailableOrHide(text.toString())
            setOnClickListener { dismiss() }
        }
    }

    override fun dismiss() {
        setHeaderImage(R.drawable.ic_line_error_24)
        setTitle(context.getString(R.string.title_alert_dialog))
        binding.dialogAlertTextviewMessage.setVisible(false)
        binding.dialogAlertTextviewPositiveAction.apply {
            text = context.getString(R.string.text_button_ok)
            setVisible(true)
            setOnClickListener { dismiss() }
        }
        binding.dialogAlertTextviewNegativeAction.apply {
            text = context.getString(R.string.text_button_cancel)
            setVisible(false)
            setOnClickListener { dismiss() }
        }
        super.dismiss()
    }
}
