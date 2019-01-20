@file:Suppress("unused")

package com.skydoves.androidveildemo

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * Developed by skydoves on 2018-10-31.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun Activity.checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun AppCompatActivity.applyToolbarMargin(toolbar: Toolbar) {
    if (checkIsMaterialVersion()) {
        toolbar.layoutParams = (toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams).apply {
            topMargin = getStatusBarSize()
        }
    }
}

private fun AppCompatActivity.getStatusBarSize(): Int {
    val idStatusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (idStatusBarHeight > 0) {
        resources.getDimensionPixelSize(idStatusBarHeight)
    } else {
        0
    }
}
