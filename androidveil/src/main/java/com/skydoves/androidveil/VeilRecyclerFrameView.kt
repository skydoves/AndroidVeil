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

@file:Suppress("CascadeIf", "SpellCheckingInspection")

package com.skydoves.androidveil

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.Shimmer

/** VeilRecyclerFrameView implements skeleton veiling for user [RecyclerView] with shimmering effect. */
@Suppress("unused", "MemberVisibilityCanBePrivate")
public class VeilRecyclerFrameView : RelativeLayout {

  private val userRecyclerView: RecyclerView = RecyclerView(context)
  private val veiledRecyclerView: RecyclerView = RecyclerView(context)
  private var veiledAdapter: VeiledAdapter? = null
  public var isVeiled: Boolean = false
    private set

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

  @LayoutRes
  private var layout = -1

  @Px
  private var radius = 8f.dp2px(this)
  private var drawable: Drawable? = null

  public var shimmer: Shimmer? = null
  public var shimmerEnable: Boolean = true
  public var defaultChildVisible: Boolean = false
  public var isPrepared: Boolean = false
  private var isItemWrapContentWidth = false
  private var isItemWrapContentHeight = true
  private val threshold = 10

  public constructor(context: Context) : super(context) {
    onCreate()
  }

  public constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    getAttrs(attrs)
    onCreate()
  }

  public constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
    context,
    attrs,
    defStyle
  ) {
    getAttrs(attrs)
    onCreate()
  }

  private fun getAttrs(attrs: AttributeSet?) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.VeilRecyclerFrameView)
    try {
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_veiled)) {
        isVeiled =
          a.getBoolean(R.styleable.VeilRecyclerFrameView_veilFrame_veiled, isVeiled)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_layout)) {
        layout = a.getResourceId(R.styleable.VeilRecyclerFrameView_veilFrame_layout, -1)
      }
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_drawable)) {
        drawable = a.getDrawable(R.styleable.VeilRecyclerFrameView_veilFrame_drawable)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_radius)) {
        radius = a.getDimension(R.styleable.VeilRecyclerFrameView_veilFrame_radius, radius)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_shimmerEnable)) {
        shimmerEnable =
          a.getBoolean(
            R.styleable.VeilRecyclerFrameView_veilFrame_shimmerEnable,
            shimmerEnable
          )
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_baseColor)) {
        baseColor =
          a.getColor(R.styleable.VeilRecyclerFrameView_veilFrame_baseColor, baseColor)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_highlightColor)) {
        highlightColor =
          a.getColor(
            R.styleable.VeilRecyclerFrameView_veilFrame_highlightColor,
            highlightColor
          )
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_baseAlpha)) {
        baseAlpha =
          a.getFloat(R.styleable.VeilRecyclerFrameView_veilFrame_baseAlpha, baseAlpha)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_highlightAlpha)) {
        highlightAlpha =
          a.getFloat(
            R.styleable.VeilRecyclerFrameView_veilFrame_highlightAlpha,
            highlightAlpha
          )
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_dropOff)) {
        dropOff = a.getFloat(R.styleable.VeilRecyclerFrameView_veilFrame_dropOff, dropOff)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_defaultChildVisible)) {
        defaultChildVisible =
          a.getBoolean(
            R.styleable.VeilRecyclerFrameView_veilFrame_defaultChildVisible,
            defaultChildVisible
          )
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_isPrepared)) {
        isPrepared =
          a.getBoolean(R.styleable.VeilRecyclerFrameView_veilFrame_isPrepared, isPrepared)
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_isItemWrapContentWidth)) {
        isItemWrapContentWidth = a.getBoolean(
          R.styleable.VeilRecyclerFrameView_veilFrame_isItemWrapContentWidth,
          isItemWrapContentWidth
        )
      }
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_isItemWrapContentHeight)) {
        isItemWrapContentHeight = a.getBoolean(
          R.styleable.VeilRecyclerFrameView_veilFrame_isItemWrapContentHeight,
          isItemWrapContentHeight
        )
      }
    } finally {
      a.recycle()
    }
  }

  private fun onCreate() {
    addView(this.userRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    addView(this.veiledRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    this.veiledRecyclerView.setHasFixedSize(true)
    applyOverScrollMode()
    when (this.isVeiled) {
      true -> visibleVeilRecyclerView()
      false -> visibleUserRecyclerView()
    }

    if (this.layout != -1) {
      setVeilLayout(layout = this.layout, isPrepared = this.isPrepared)
    }
  }

  /** Sets mask layout */
  public fun setVeilLayout(
    @LayoutRes layout: Int,
    isPrepared: Boolean = false,
    onItemClickListener: VeiledItemOnClickListener? = null,
  ) {
    this.veiledAdapter =
      VeiledAdapter(
        userLayout = layout,
        isPrepared = isPrepared,
        onItemClickListener = onItemClickListener,
        isListItemWrapContentWidth = isItemWrapContentWidth,
        isListItemWrapContentHeight = isItemWrapContentHeight
      )
    this.veiledRecyclerView.adapter = this.veiledAdapter
    requestLayout()
  }

  /** Sets mask layout and adds masked items. */
  public fun setVeilLayout(
    @LayoutRes layout: Int,
    @IntRange(from = 1) size: Int,
    isPrepared: Boolean,
    onItemClickListener: VeiledItemOnClickListener? = null,
  ) {
    this.setVeilLayout(
      layout = layout,
      isPrepared = isPrepared,
      onItemClickListener = onItemClickListener
    )
    this.addVeiledItems(size)
    requestLayout()
  }

  /** Adds masked items. */
  public fun addVeiledItems(@IntRange(from = 1) size: Int) {
    val paramList = ArrayList<VeilParams>()
    for (i in 0 until size) {
      paramList.add(
        VeilParams(
          baseColor = baseColor,
          highlightColor = highlightColor,
          drawable = drawable,
          radius = radius,
          baseAlpha = baseAlpha,
          highlightAlpha = highlightAlpha,
          dropOff = dropOff,
          shimmerEnable = shimmerEnable,
          shimmer = shimmer,
          defaultChildVisible = defaultChildVisible
        )
      )
    }
    this.veiledAdapter?.updateParams(paramList)
  }

  /** Sets userRecyclerView's adapter. */
  public fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.userRecyclerView.adapter = adapter
    this.invalidate()
  }

  /** Sets userRecyclerView's adapter and RecyclerViews LayoutManager. */
  public fun setAdapter(
    adapter: RecyclerView.Adapter<*>?,
    layoutManager: RecyclerView.LayoutManager,
  ) {
    this.setAdapter(adapter)
    this.setLayoutManager(layoutManager)
  }

  /** Sets RecyclerViews LayoutManager. */
  public fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
    this.userRecyclerView.layoutManager = layoutManager
    when (layoutManager) {
      is GridLayoutManager ->
        this.veiledRecyclerView.layoutManager =
          GridLayoutManager(context, layoutManager.spanCount)

      is StaggeredGridLayoutManager ->
        this.veiledRecyclerView.layoutManager =
          StaggeredGridLayoutManager(layoutManager.spanCount, layoutManager.orientation)

      is LinearLayoutManager ->
        this.veiledRecyclerView.layoutManager =
          LinearLayoutManager(
            context,
            layoutManager.orientation,
            layoutManager.reverseLayout
          )

      else -> this.veiledRecyclerView.layoutManager
    }
  }

  /** Make appear the mask. */
  public fun veil() {
    this.veiledAdapter?.let {
      if (!this.isVeiled) {
        this.isVeiled = true
        visibleVeilRecyclerView()
      }
    }
  }

  /** Make disappear the mask. */
  public fun unVeil() {
    if (this.isVeiled) {
      this.isVeiled = false
      visibleUserRecyclerView()
    }
  }

  /** Returns a veiled recyclerView. */
  public fun getVeiledRecyclerView(): RecyclerView {
    return this.veiledRecyclerView
  }

  /** Returns a user recyclerView. */
  public fun getRecyclerView(): RecyclerView {
    return this.userRecyclerView
  }

  /** Visible veiledRecyclerView and Invisible userRecyclerView. */
  private fun visibleVeilRecyclerView() {
    this.veiledRecyclerView.visible()
    this.veiledRecyclerView.bringToFront()
    this.userRecyclerView.gone()
  }

  /** Invisible veiledRecyclerView and Visible userRecyclerView. */
  private fun visibleUserRecyclerView() {
    this.userRecyclerView.visible()
    this.userRecyclerView.bringToFront()
    this.veiledRecyclerView.gone()
  }

  /** Apply overscrollModel of parent to veiled and user recyclerview. */
  private fun applyOverScrollMode() {
    this.veiledRecyclerView.overScrollMode = overScrollMode
    this.userRecyclerView.overScrollMode = overScrollMode
  }
}
