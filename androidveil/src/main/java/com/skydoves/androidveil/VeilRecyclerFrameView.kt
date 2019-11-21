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

@file:Suppress("CascadeIf", "SpellCheckingInspection")

package com.skydoves.androidveil

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.Shimmer
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator

/** VeilRecyclerFrameView implements skeleton veiling for user [RecyclerView] with shimmering effect. */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class VeilRecyclerFrameView : RelativeLayout {

  private val userRecyclerView: RecyclerView = RecyclerView(context)

  private val veiledRecyclerView: RecyclerView = RecyclerView(context)
  private var veiledAdapter: VeiledAdapter? = null

  private var isVeiled = false

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
  private var radius = 8f.dp2px(resources)
  private var drawable: Drawable? = null

  var shimmer: Shimmer? = null
  var shimmerEnable: Boolean = true
  var defaultChildVisible = false
  private val threshold = 10

  constructor(context: Context) : super(context) {
    onCreate()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    getAttrs(attrs)
    onCreate()
  }

  constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,
    defStyle) {
    getAttrs(attrs)
    onCreate()
  }

  private fun getAttrs(attrs: AttributeSet?) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.VeilRecyclerFrameView)
    try {
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_veiled))
        isVeiled = a.getBoolean(R.styleable.VeilRecyclerFrameView_veilFrame_veiled, isVeiled)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_layout))
        layout = a.getResourceId(R.styleable.VeilRecyclerFrameView_veilFrame_layout, -1)
      if (a.hasValue(R.styleable.VeilLayout_veilLayout_drawable))
        drawable = a.getDrawable(R.styleable.VeilRecyclerFrameView_veilFrame_drawable)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_radius))
        radius = a.getDimension(R.styleable.VeilRecyclerFrameView_veilFrame_radius, radius)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_shimmerEnable))
        shimmerEnable =
          a.getBoolean(R.styleable.VeilRecyclerFrameView_veilFrame_shimmerEnable, shimmerEnable)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_baseColor))
        baseColor = a.getColor(R.styleable.VeilRecyclerFrameView_veilFrame_baseColor, baseColor)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_highlightColor))
        highlightColor =
          a.getColor(R.styleable.VeilRecyclerFrameView_veilFrame_highlightColor, highlightColor)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_baseAlpha))
        baseAlpha = a.getFloat(R.styleable.VeilRecyclerFrameView_veilFrame_baseAlpha, baseAlpha)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_highlightAlpha))
        highlightAlpha =
          a.getFloat(R.styleable.VeilRecyclerFrameView_veilFrame_highlightAlpha, highlightAlpha)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_dropOff))
        dropOff = a.getFloat(R.styleable.VeilRecyclerFrameView_veilFrame_dropOff, dropOff)
      if (a.hasValue(R.styleable.VeilRecyclerFrameView_veilFrame_defaultChildVisible))
        defaultChildVisible =
          a.getBoolean(R.styleable.VeilRecyclerFrameView_veilFrame_defaultChildVisible,
            defaultChildVisible)
    } finally {
      a.recycle()
    }
  }

  private fun onCreate() {
    RecyclerViewPaginator(
      recyclerView = veiledRecyclerView,
      onLast = { false },
      loadMore = { veiledRecyclerView.post { veiledAdapter?.update(it, threshold) } },
      isLoading = { false }
    ).apply { threshold = this@VeilRecyclerFrameView.threshold }
    addView(userRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    addView(veiledRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    when (this.isVeiled) {
      true -> visibleVeilRecyclerView()
      false -> visibleUserRecyclerView()
    }

    if (this.layout != -1)
      setVeilLayout(this.layout)
  }

  /** Sets mask layout. */
  fun setVeilLayout(layout: Int) {
    this.veiledAdapter = VeiledAdapter(layout)
    this.veiledRecyclerView.adapter = this.veiledAdapter
  }

  /** Sets mask layout and VeiledItemOnClickListener. */
  fun setVeilLayout(layout: Int, onItemClickListener: VeiledItemOnClickListener) {
    this.veiledAdapter = VeiledAdapter(layout, onItemClickListener)
    this.veiledRecyclerView.adapter = this.veiledAdapter
  }

  /** Sets mask layout and adds masked items. */
  fun setVeilLayout(layout: Int, size: Int) {
    this.setVeilLayout(layout)
    this.addVeiledItems(size)
  }

  /** Sets mask layout and VeiledItemOnClickListener and adds masked items. */
  fun setVeilLayout(layout: Int, onItemClickListener: VeiledItemOnClickListener, size: Int) {
    this.setVeilLayout(layout, onItemClickListener)
    this.addVeiledItems(size)
  }

  /** Adds masked items. */
  fun addVeiledItems(size: Int) {
    val paramList = ArrayList<VeilParams>()
    for (i in 0 until size) {
      paramList.add(
        VeilParams(
          baseColor,
          highlightColor,
          drawable,
          radius,
          baseAlpha,
          highlightAlpha,
          dropOff,
          shimmerEnable,
          shimmer,
          defaultChildVisible)
      )
    }
    this.veiledAdapter?.updateParams(paramList)
  }

  /** Sets userRecyclerView's adapter. */
  fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.userRecyclerView.adapter = adapter
    this.invalidate()
  }

  /** Sets userRecyclerView's adapter and RecyclerViews LayoutManager. */
  fun setAdapter(adapter: RecyclerView.Adapter<*>?, layoutManager: RecyclerView.LayoutManager) {
    this.setAdapter(adapter)
    this.setLayoutManager(layoutManager)
  }

  /** Sets RecyclerViews LayoutManager. */
  fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
    if (layoutManager is GridLayoutManager) {
      this.userRecyclerView.layoutManager = layoutManager
      this.veiledRecyclerView.layoutManager = GridLayoutManager(context, layoutManager.spanCount)
    } else if (layoutManager is StaggeredGridLayoutManager) {
      this.userRecyclerView.layoutManager = layoutManager
      this.veiledRecyclerView.layoutManager =
        StaggeredGridLayoutManager(layoutManager.spanCount, layoutManager.orientation)
    } else if (layoutManager is LinearLayoutManager) { // This condition should be at the last.
      this.userRecyclerView.layoutManager = layoutManager
      this.veiledRecyclerView.layoutManager = LinearLayoutManager(context)
    }
  }

  /** Make appear the mask. */
  fun veil() {
    this.veiledAdapter?.let {
      if (!this.isVeiled) {
        this.isVeiled = true
        visibleVeilRecyclerView()
      }
    }
  }

  /** Make disappear the mask. */
  fun unVeil() {
    if (this.isVeiled) {
      this.isVeiled = false
      visibleUserRecyclerView()
    }
  }

  /** Visible veiledRecyclerView and Invisible userRecyclerView. */
  private fun visibleVeilRecyclerView() {
    this.veiledRecyclerView.visible()
    this.veiledRecyclerView.bringToFront()
    this.userRecyclerView.invisible()
  }

  /** Invisible veiledRecyclerView and Visible userRecyclerView. */
  private fun visibleUserRecyclerView() {
    this.userRecyclerView.visible()
    this.userRecyclerView.bringToFront()
    this.veiledRecyclerView.invisible()
  }

  /** Returns veiled recyclerView */
  fun getVeiledRecyclerView(): RecyclerView {
    return this.veiledRecyclerView
  }

  /** Returns user's recyclerView */
  fun getRecyclerView(): RecyclerView {
    return this.userRecyclerView
  }
}
