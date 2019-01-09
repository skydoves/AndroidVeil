/*
 * Copyright (C) 2018 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.androidveil

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.*

class VeilLayout : FrameLayout {

    @ColorInt
    private var baseColor = Color.LTGRAY
    @ColorInt
    private var highlightColor = Color.DKGRAY
    private var baseAlpha = 1.0f
    private var highlightAlpha = 1.0f
    private var dropOff = 0.5f

    @LayoutRes
    var layout = -1
        set(value) {
            field = value
            reDrawLayout(value)
        }

    var isVeiled = false
        private set

    private val maskElements = ArrayList<View>()
    val shimmerContainer = ShimmerFrameLayout(context)
    val nonShimmer = Shimmer.AlphaHighlightBuilder().setBaseAlpha(1.0f).setDropoff(1.0f).build()
    var shimmer = Shimmer.AlphaHighlightBuilder().build()
        set(value) {
            field = value
            shimmerContainer.setShimmer(value)
        }
    var shimmerEnable: Boolean = true
        set(value) {
            field = value
            when (value) {
                true -> shimmerContainer.setShimmer(shimmer)
                false -> shimmerContainer.setShimmer(nonShimmer)
            }
        }

    constructor(context: Context) : super(context) {
        onCreate()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs)
        onCreate()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(attrs)
        onCreate()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        getAttrs(attrs)
        onCreate()
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.VeilLayout)
        try {
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_veiled))
                isVeiled = a.getBoolean(R.styleable.VeilLayout_veilLayout_veiled, isVeiled)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_layout))
                layout = a.getResourceId(R.styleable.VeilLayout_veilLayout_layout, -1)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_shimmerEnable))
                shimmerEnable = a.getBoolean(R.styleable.VeilLayout_veilLayout_shimmerEnable, shimmerEnable)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_baseColor))
                baseColor = a.getColor(R.styleable.VeilLayout_veilLayout_baseColor, baseColor)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_highlightColor))
                highlightColor = a.getColor(R.styleable.VeilLayout_veilLayout_highlightColor, highlightColor)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_baseAlpha))
                baseAlpha = a.getFloat(R.styleable.VeilLayout_veilLayout_baseAlpha, baseAlpha)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_highlightAlpha))
                highlightAlpha = a.getFloat(R.styleable.VeilLayout_veilLayout_highlightAlpha, highlightAlpha)
            if (a.hasValue(R.styleable.VeilLayout_veilLayout_dropOff))
                dropOff = a.getFloat(R.styleable.VeilLayout_veilLayout_dropOff, dropOff)
        } finally {
            a.recycle()
        }
    }

    private fun onCreate() {
        shimmerContainer.invisible()
        val shimmerBuilder = Shimmer.ColorHighlightBuilder()
        shimmerBuilder.setBaseColor(baseColor).setHighlightColor(highlightColor)
        shimmerBuilder.setBaseAlpha(baseAlpha).setDropoff(highlightAlpha).setDropoff(dropOff)
        shimmerBuilder.setAutoStart(false)
        shimmer = shimmerBuilder.build()
        shimmerEnable = shimmerEnable
    }

    /** Remove previous views and inflate a new layout. */
    private fun reDrawLayout(layout: Int) {
        removeAllViews()
        LayoutInflater.from(context).inflate(layout, this, true)
        onFinishInflate()
    }

    /** Call addMaskElements method after inflating. */
    override fun onFinishInflate() {
        super.onFinishInflate()
        removeView(shimmerContainer)
        addView(shimmerContainer)
        addMaskElements(this)
    }

    /**
     * Called when addMaskElements is called.
     * Adds masked views by viewTree structure except for ViewGroup.
     */
    private fun addMaskElements(parent: ViewGroup) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            child.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                @Suppress("DEPRECATION")
                override fun onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }

                    if (child is ViewGroup) {
                        addMaskElements(child)
                    } else {
                        var marginX = 0f
                        var marginY = 0f
                        var parentUpper = parent.parent
                        while ((parentUpper !is VeilLayout)) {
                            if (parentUpper is ViewGroup) {
                                val params = parentUpper.layoutParams
                                if (params is MarginLayoutParams) {
                                    marginX += parentUpper.x
                                    marginY += parentUpper.y
                                }
                                parentUpper = parentUpper.parent
                            } else {
                                break
                            }
                        }

                        // create a masked view
                        val view = View(context)
                        view.layoutParams = FrameLayout.LayoutParams(child.measuredWidth, child.measuredHeight)
                        view.x = marginX + parent.x + child.x
                        view.y = marginY + parent.y + child.y
                        view.setBackgroundColor(baseColor)
                        maskElements.add(view)
                        shimmerContainer.addView(view)
                    }
                }
            })
        }

        // Invalidate the whole masked view.
        invalidate()

        // Auto veiled
        isVeiled = !isVeiled
        when (isVeiled) {
            true -> unVeil()
            false -> veil()
        }
    }

    /** Make appear the mask. */
    fun veil() {
        if (!isVeiled) {
            isVeiled = true
            startShimmer()
            invalidate()
        }
    }

    /** Make disappear the mask. */
    fun unVeil() {
        if (isVeiled) {
            isVeiled = false
            stopShimmer()
            invalidate()
        }
    }

    /** Starts the shimmer animation. */
    fun startShimmer() {
        shimmerContainer.visible()
        if (shimmerEnable) {
            shimmerContainer.startShimmer()
        }
    }

    /** Stops the shimmer animation. */
    fun stopShimmer() {
        shimmerContainer.invisible()
        shimmerContainer.stopShimmer()
    }

    /** Invalidate VeilLayout & Shimmer */
    override fun invalidate() {
        super.invalidate()
        shimmerContainer.invalidate()
    }
}
