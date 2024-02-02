/*
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
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
@JvmSynthetic
public inline fun alphaShimmer(crossinline block: Shimmer.AlphaHighlightBuilder.() -> Unit): Shimmer =
  Shimmer.AlphaHighlightBuilder().apply(block).build()

/** create a [Shimmer] by [Shimmer.ColorHighlightBuilder] using dsl. */
@JvmSynthetic
public inline fun colorShimmer(crossinline block: Shimmer.ColorHighlightBuilder.() -> Unit): Shimmer =
  Shimmer.ColorHighlightBuilder().apply(block).build()

/** VeilLayout creates skeletons about the complex child views with shimmering effect. */
@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
public class VeilLayout : FrameLayout {

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
  public var radius: Float = 8f.dp2px(this)
  public var drawable: Drawable? = null

  @LayoutRes
  private var layout: Int = -1
  private var isPrepared: Boolean = false

  public fun setLayout(@LayoutRes layout: Int, isPrepared: Boolean = false) {
    this.layout = layout
    this.isPrepared = isPrepared
    if (isPrepared) {
      defaultChildVisible = true
    }
    invalidateLayout(layout)
  }

  @LayoutRes
  public fun getLayout(): Int = layout

  public var isVeiled: Boolean = false
    private set

  public val shimmerContainer: ShimmerFrameLayout = ShimmerFrameLayout(context)
  public val nonShimmer: Shimmer =
    Shimmer.AlphaHighlightBuilder().setBaseAlpha(1.0f).setDropoff(1.0f).build()
  public var shimmer: Shimmer = Shimmer.AlphaHighlightBuilder().build()
    set(value) {
      field = value
      shimmerContainer.setShimmer(value)
      getPreparedView()?.setShimmer(value)
    }
  public var shimmerEnable: Boolean = true
    set(value) {
      field = value
      when (value) {
        true -> {
          shimmerContainer.setShimmer(shimmer)
          getPreparedView()?.setShimmer(shimmer)
        }

        false -> {
          shimmerContainer.setShimmer(nonShimmer)
          getPreparedView()?.setShimmer(nonShimmer)
        }
      }
    }
  public var defaultChildVisible: Boolean = false

  public constructor(context: Context) : super(context) {
    onCreate()
  }

  public constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    getAttrs(attrs)
    onCreate()
  }

  public constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    getAttrs(attrs)
    onCreate()
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
  ) : super(
    context,
    attrs,
    defStyleAttr,
    defStyleRes
  ) {
    getAttrs(attrs)
    onCreate()
  }

  private fun getAttrs(attrs: AttributeSet?) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.VeilLayout)
    try {
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_veiled)) {
        isVeiled = a.getBoolean(R.styleable.VeilLayout_veilLayout_veiled, isVeiled)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_layout)) {
        layout = a.getResourceId(R.styleable.VeilLayout_veilLayout_layout, -1)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_drawable)) {
        drawable = a.getDrawable(R.styleable.VeilLayout_veilLayout_drawable)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_radius)) {
        radius = a.getDimension(R.styleable.VeilLayout_veilLayout_radius, radius)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_shimmerEnable)) {
        shimmerEnable = a.getBoolean(R.styleable.VeilLayout_veilLayout_shimmerEnable, shimmerEnable)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_baseColor)) {
        baseColor = a.getColor(R.styleable.VeilLayout_veilLayout_baseColor, baseColor)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_highlightColor)) {
        highlightColor =
          a.getColor(R.styleable.VeilLayout_veilLayout_highlightColor, highlightColor)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_baseAlpha)) {
        baseAlpha = a.getFloat(R.styleable.VeilLayout_veilLayout_baseAlpha, baseAlpha)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_highlightAlpha)) {
        highlightAlpha =
          a.getFloat(R.styleable.VeilLayout_veilLayout_highlightAlpha, highlightAlpha)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_dropOff)) {
        dropOff = a.getFloat(R.styleable.VeilLayout_veilLayout_dropOff, dropOff)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_defaultChildVisible)) {
        defaultChildVisible =
          a.getBoolean(R.styleable.VeilLayout_veilLayout_defaultChildVisible, defaultChildVisible)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_isPrepared)) {
        isPrepared =
          a.getBoolean(R.styleable.VeilLayout_veilLayout_isPrepared, isPrepared)
      }
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
  private fun invalidateLayout(@LayoutRes layout: Int) {
    setLayout(LayoutInflater.from(context).inflate(layout, this, false))
  }

  /** Remove previous views and inflate a new layout using an inflated view. */
  public fun setLayout(layout: View) {
    require(!isPrepared || layout is ShimmerFrameLayout) {
      "If you place a 'prepared' Layout, then it must be a ShimmerFrameLayout"
    }
    removeAllViews()
    addView(layout)
    shimmerContainer.removeAllViews()
    onFinishInflate()
  }

  /** Invokes addMaskElements method after inflating. */
  override fun onFinishInflate() {
    super.onFinishInflate()
    removeView(shimmerContainer)
    if (!isPrepared) {
      // The layout is not pre-shimmering, this VeilLayout will  try to make a close representation
      addView(shimmerContainer)
      addMaskElements(this)
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

  /**
   * Adds a masked skeleton views depending on the view tree structure of the [VeilLayout].
   * This method will ignore the ViewGroup for creating masked skeletons.
   *
   * @param parent A parent view for creating the masked skeleton.
   */
  private fun addMaskElements(parent: ViewGroup) {
    (0 until parent.childCount).map { parent.getChildAt(it) }.forEach { child ->
      child.post {
        if (child is ViewGroup) {
          addMaskElements(child)
        } else {
          val (marginX, marginY) = findMargins(parent)
          val view = createMaskedView(child, marginX, parent, marginY)
          shimmerContainer.addView(view)
        }
      }
    }
  }

  private fun createMaskedView(
    child: View,
    marginX: Float,
    parent: ViewGroup,
    marginY: Float
  ): View {
    return View(context).apply {
      layoutParams = LayoutParams(child.width, child.height)
      x = marginX + parent.x + child.x
      y = marginY + parent.y + child.y
      setBackgroundColor(baseColor)

      background = drawable ?: GradientDrawable().apply {
        setColor(Color.DKGRAY)
        cornerRadius = radius
      }
    }
  }

  private fun findMargins(parent: ViewGroup): Pair<Float, Float> {
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
    return Pair(marginX, marginY)
  }

  /** Make appear the mask. */
  public fun veil() {
    if (!this.isVeiled) {
      this.isVeiled = true
      startShimmer()
      invalidate()
    }
  }

  /** Make disappear the mask. */
  public fun unVeil() {
    if (this.isVeiled) {
      this.isVeiled = false
      stopShimmer()
      invalidate()
    }
  }

  /** Starts the shimmer animation. */
  public fun startShimmer() {
    if (shimmerEnable) {
      if (isPrepared) {
        getPreparedView()?.apply {
          setShimmer(shimmer)
          visible()
          startShimmer()
        }
      } else {
        shimmerContainer.visible()
        shimmerContainer.startShimmer()
      }
    }
    if (!this.defaultChildVisible) {
      setChildVisibility(false)
    }
  }

  /** Stops the shimmer animation. */
  public fun stopShimmer() {
    this.shimmerContainer.invisible()
    this.shimmerContainer.stopShimmer()
    if (!this.defaultChildVisible) {
      setChildVisibility(true)
    }
  }

  private fun setChildVisibility(visible: Boolean) {
    (0 until childCount).map { getChildAt(it) }.forEach { child ->
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

  private fun getPreparedView(): ShimmerFrameLayout? {
    if (childCount > 0) {
      val view = getChildAt(0)
      if (view is ShimmerFrameLayout) return view
    }
    return null
  }
}
