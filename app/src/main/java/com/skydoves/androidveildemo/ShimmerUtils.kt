package com.skydoves.androidveildemo

import android.content.Context
import android.support.v4.content.ContextCompat
import com.facebook.shimmer.Shimmer

/**
 * Developed by skydoves on 2018-10-31.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object ShimmerUtils {
    fun getGrayShimmer(context: Context): Shimmer {
        return Shimmer.ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(context, R.color.shimmerBase0))
                .setHighlightColor(ContextCompat.getColor(context, R.color.shimmerHighlight0))
                .setBaseAlpha(0.8f)
                .setHighlightAlpha(0.8f)
                .build()
    }
}
