package com.azizutku.movie.extensions

import android.view.View
import android.widget.TextView

fun View.setVisible(isVisible: Boolean, invisible: Boolean = false) {
    this.visibility = when {
        isVisible -> View.VISIBLE
        invisible -> View.INVISIBLE
        else -> View.GONE
    }
}

fun TextView.setTextIfAvailableOrHide(text: String?, vararg otherViewsToBeHidden: View) {
    this.text = text
    setVisible(text.isNullOrEmpty().not())
    otherViewsToBeHidden.forEach {
        it.setVisible(text.isNullOrEmpty().not())
    }
}
