package com.azizutku.movie.core.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.azizutku.movie.core.ui.extensions.setTextIfAvailableOrHide
import com.azizutku.movie.core.ui.extensions.setVisible
import com.azizutku.movie.core.ui.xml.R
import com.azizutku.movie.core.ui.xml.databinding.LayoutDialogAlertBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import com.azizutku.movie.core.ui.common.R as coreUiCommonR

class AlertDialog @Inject constructor(@ActivityContext context: Context) : Dialog(context, R.style.Theme_Dialog) {
    private val binding: LayoutDialogAlertBinding = LayoutDialogAlertBinding.inflate(
        LayoutInflater.from(context),
        null,
        false
    )

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
        setHeaderImage(coreUiCommonR.drawable.ic_line_error_24)
        setTitle(context.getString(coreUiCommonR.string.title_alert_dialog))
        binding.dialogAlertTextviewMessage.setVisible(false)
        binding.dialogAlertTextviewPositiveAction.apply {
            text = context.getString(coreUiCommonR.string.text_button_ok)
            setVisible(true)
            setOnClickListener { dismiss() }
        }
        binding.dialogAlertTextviewNegativeAction.apply {
            text = context.getString(coreUiCommonR.string.text_button_cancel)
            setVisible(false)
            setOnClickListener { dismiss() }
        }
        super.dismiss()
    }
}
