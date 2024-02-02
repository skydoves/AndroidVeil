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

package com.skydoves.androidveil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.androidveil.databinding.VeilItemLayoutBinding

internal class VeiledAdapter(
  @LayoutRes private val userLayout: Int,
  private val isPrepared: Boolean = false,
  private val onItemClickListener: VeiledItemOnClickListener? = null,
  private val isListItemWrapContentWidth: Boolean = false,
  private val isListItemWrapContentHeight: Boolean = true
) : RecyclerView.Adapter<VeiledAdapter.VeiledViewHolder>() {

  private val veilParamList: MutableList<VeilParams> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiledViewHolder {
    val binding = VeilItemLayoutBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return VeiledViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
          ?: return@setOnClickListener
        onItemClickListener?.onItemClicked(position)
      }
    }
  }

  override fun onBindViewHolder(holder: VeiledViewHolder, position: Int) {
    val veilParams = getVeilParams(position)
    with(holder.binding.itemVeilLayoutMain) {
      layoutParams = ViewGroup.LayoutParams(
        getLayoutParams(isListItemWrapContentWidth),
        getLayoutParams(isListItemWrapContentHeight)
      )
      if (getLayout() == -1) {
        setLayout(userLayout, isPrepared)
        veilParams.shimmer?.let {
          shimmer = it
        } ?: let {
          shimmer = colorShimmer {
            setBaseColor(veilParams.baseColor)
            setBaseAlpha(veilParams.baseAlpha)
            setHighlightColor(veilParams.highlightColor)
            setHighlightAlpha(veilParams.highlightAlpha)
            setDropoff(veilParams.dropOff)
          }
        }
        radius = veilParams.radius
        drawable = veilParams.drawable
        shimmerEnable = veilParams.shimmerEnable
        // Make sure prepared layout (which is the first child view) is always visible
        defaultChildVisible = veilParams.defaultChildVisible || isPrepared
      } else {
        startShimmer()
      }
      veil()
    }
  }

  private fun getLayoutParams(isWrapContent: Boolean): Int {
    return if (isWrapContent) {
      ViewGroup.LayoutParams.WRAP_CONTENT
    } else {
      ViewGroup.LayoutParams.MATCH_PARENT
    }
  }

  private fun getVeilParams(position: Int): VeilParams = veilParamList[position]

  fun updateParams(params: List<VeilParams>) {
    veilParamList.clear()
    veilParamList.addAll(params)
    notifyDataSetChanged()
  }

  override fun getItemCount() = veilParamList.size

  class VeiledViewHolder(val binding: VeilItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
