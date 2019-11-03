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

import android.view.View
import androidx.annotation.LayoutRes
import com.skydoves.baserecyclerviewadapter.BaseViewHolder

internal class VeiledViewHolder(
  view: View,
  @LayoutRes val layout: Int,
  private val onItemClickListener: VeiledItemOnClickListener? = null
) : BaseViewHolder(view) {

  private lateinit var veilParams: VeilParams

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is VeilParams) {
      veilParams = data
      drawItem()
    }
  }

  /** Draw ViewHolderItem by data */
  private fun drawItem() {
    if (itemView is VeilLayout && itemView.layout == -1) {
      itemView.layout = layout
      veilParams.shimmer?.let {
        itemView.shimmer = it
      } ?: let {
        val shimmer = colorShimmer {
          setBaseColor(veilParams.baseColor)
          setBaseAlpha(veilParams.baseAlpha)
          setHighlightColor(veilParams.highlightColor)
          setHighlightAlpha(veilParams.highlightAlpha)
          setDropoff(veilParams.dropOff)
        }
        itemView.shimmer = shimmer
      }
      itemView.radius = veilParams.radius
      itemView.drawable = veilParams.drawable
      itemView.shimmerEnable = veilParams.shimmerEnable
      itemView.defaultChildVisible = veilParams.defaultChildVisible
      itemView.veil()
    } else if (itemView is VeilLayout) {
      itemView.veil()
      itemView.startShimmer()
    }
  }

  /** Event OnClickListener */
  override fun onClick(p0: View?) {
    onItemClickListener?.onItemClicked(adapterPosition)
  }

  override fun onLongClick(p0: View?) = false
}
