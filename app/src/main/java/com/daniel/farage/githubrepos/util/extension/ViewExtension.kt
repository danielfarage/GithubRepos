package com.daniel.farage.githubrepos.util.extension

import android.view.View

fun View.isVisible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}