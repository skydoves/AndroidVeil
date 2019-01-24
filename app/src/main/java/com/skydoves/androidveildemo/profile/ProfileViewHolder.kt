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

package com.skydoves.androidveildemo.profile

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_profile.view.*

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ProfileViewHolder(
    private val view: View,
    private val delegate: Delegate
) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClickListener(profile: Profile)
    }

    private lateinit var profileItem: Profile

    override fun bindData(data: Any) {
        if (data is Profile) {
            this.profileItem = data
            drawItemUI()
        }
    }

    private fun drawItemUI() {
        itemView.run {
            profileItem.image?.let { profile.setImageDrawable(it) }
            name.text = profileItem.name
            name.visibility = View.VISIBLE
            content.text = profileItem.content
            content.visibility = View.VISIBLE
        }
    }

    override fun onClick(p0: View?) {
        delegate.onItemClickListener(profile = profileItem)
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }
}
