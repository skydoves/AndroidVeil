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
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow

class VeiledAdapter(
    @LayoutRes private val userLayout: Int,
    private val onItemClickListener: VeiledItemOnClickListener? = null
)
    : BaseAdapter() {

    fun addParams(params: List<VeilParams>) {
        clearSections()
        addSection(ArrayList<VeilParams>())
        addItemsOnSection(0, params)
        notifyDataSetChanged()
    }

    fun update(position: Int, threshold: Int) {
        when (sections[0].size > position) {
            true -> notifyItemChanged(position)
            false -> {
                for (i in position..position + threshold) {
                    notifyItemChanged(i)
                }
            }
        }
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_veiled_layout
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return VeiledViewHolder(view, userLayout, onItemClickListener)
    }
}
