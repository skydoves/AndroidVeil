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

@file:Suppress("unused")

package com.skydoves.androidveil

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

/** create a [Shimmer] by [Shimmer.AlphaHighlightBuilder] using dsl. */
fun alphaShimmer(block: Shimmer.AlphaHighlightBuilder.() -> Unit): Shimmer =
  Shimmer.AlphaHighlightBuilder().apply(block).build()

/** create a [Shimmer] by [Shimmer.ColorHighlightBuilder] using dsl. */
fun colorShimmer(block: Shimmer.ColorHighlightBuilder.() -> Unit): Shimmer =
  Shimmer.ColorHighlightBuilder().apply(block).build()

/** VeilLayout creates skeletons about the complex child views with shimmering effect. */
@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
class VeilLayout : FrameLayout {

  @ColorInt
  private var baseColor = Color.LTGRAY
  @ColorInt
  private var highlightColor = Color.DKGRAY
  @FloatRange(from = 0.0, to = 1.0)
  private var baseAlpha = 1.0f
  @FloatRange(from = 0.0, to = 1.0)
  private var highlightAlpha = 1.0f
  @FloatRange(from = 0.0, to = 1.0)
  private var dropOff = 0.5f
  @Px
  var radius = 8f.dp2px(this)
  var drawable: Drawable? = null

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
  var defaultChildVisible = false

  constructor(context: Context) : super(context) {
    onCreate()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    getAttrs(attrs)
    onCreate()
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
    defStyleAttr) {
    getAttrs(attrs)
    onCreate()
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
    context, attrs, defStyleAttr, defStyleRes) {
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
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_drawable))
        drawable = a.getDrawable(R.styleable.VeilLayout_veilLayout_drawable)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_radius))
        radius = a.getDimension(R.styleable.VeilLayout_veilLayout_radius, radius)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_shimmerEnable))
        shimmerEnable = a.getBoolean(R.styleable.VeilLayout_veilLayout_shimmerEnable, shimmerEnable)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_baseColor))
        baseColor = a.getColor(R.styleable.VeilLayout_veilLayout_baseColor, baseColor)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_highlightColor))
        highlightColor =
          a.getColor(R.styleable.VeilLayout_veilLayout_highlightColor, highlightColor)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_baseAlpha))
        baseAlpha = a.getFloat(R.styleable.VeilLayout_veilLayout_baseAlpha, baseAlpha)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_highlightAlpha))
        highlightAlpha =
          a.getFloat(R.styleable.VeilLayout_veilLayout_highlightAlpha, highlightAlpha)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_dropOff))
        dropOff = a.getFloat(R.styleable.VeilLayout_veilLayout_dropOff, dropOff)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_defaultChildVisible))
        defaultChildVisible =
          a.getBoolean(R.styleable.VeilLayout_veilLayout_defaultChildVisible, defaultChildVisible)
    } finally {
      a.recycle()
    }
  }

  private fun onCreate() {
    this.shimmerContainer.invisible()
    Shimmer.ColorHighlightBuilder().apply {
      setBaseColor(baseColor).setHighlightColor(highlightColor)
      setBaseAlpha(baseAlpha).setDropoff(highlightAlpha).setDropoff(dropOff)
      setAutoStart(false)
    }.also { this.shimmer = it.build() }
    this.shimmerEnable = this.shimmerEnable // calling for backing field
  }

  /** Remove previous views and inflate a new layout using layout resource */
  private fun reDrawLayout(@LayoutRes layout: Int) {
    setLayout(LayoutInflater.from(context).inflate(layout, this, false))
  }

  /** Remove previous views and inflate a new layout using an inflated view. */
  fun setLayout(layout: View) {
    removeAllViews()
    addView(layout)
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
      child.post {
        if (child is ViewGroup) {
          addMaskElements(child)
        } else {
          var marginX = 0f
          var marginY = 0f
          var grandParent = parent.parent
          while (grandParent !is VeilLayout) {
            if (grandParent is ViewGroup) {
              val params = grandParent.layoutParams
              if (params is MarginLayoutParams) {
                marginX += grandParent.x
                marginY += grandParent.y
              }
              grandParent = grandParent.parent
            } else {
              break
            }
          }

          // create a masked view
          View(context).apply {
            layoutParams = LayoutParams(child.width, child.height)
            x = marginX + parent.x + child.x
            y = marginY + parent.y + child.y
            setBackgroundColor(baseColor)

            if (drawable != null) {
              background = drawable
            } else {
              background = GradientDrawable().apply {
                setColor(Color.DKGRAY)
                cornerRadius = radius
              }
            }

            maskElements.add(this)
            shimmerContainer.addView(this)
          }
        }
      }
    }

    // Invalidate the whole masked view.
    invalidate()

    // Auto veiled
    this.isVeiled = !this.isVeiled
    when (this.isVeiled) {
      true -> unVeil()
      false -> veil()
    }
  }

  /** Make appear the mask. */
  fun veil() {
    if (!this.isVeiled) {
      this.isVeiled = true
      startShimmer()
      invalidate()
    }
  }

  /** Make disappear the mask. */
  fun unVeil() {
    if (this.isVeiled) {
      this.isVeiled = false
      stopShimmer()
      invalidate()
    }
  }

  /** Starts the shimmer animation. */
  fun startShimmer() {
    this.shimmerContainer.visible()
    if (this.shimmerEnable) {
      this.shimmerContainer.startShimmer()
    }
    if (!this.defaultChildVisible) {
      setChildVisibility(false)
    }
  }

  /** Stops the shimmer animation. */
  fun stopShimmer() {
    this.shimmerContainer.invisible()
    this.shimmerContainer.stopShimmer()
    if (!this.defaultChildVisible) {
      setChildVisibility(true)
    }
  }

  private fun setChildVisibility(visible: Boolean) {
    for (i in 0 until childCount) {
      val child = getChildAt(i)
      if (child != this.shimmerContainer) {
        child.visible(visible)
      }
    }
  }

  /** Invalidate VeilLayout & Shimmer */
  override fun invalidate() {
    super.invalidate()
    this.shimmerContainer.invalidate()
  }
}
