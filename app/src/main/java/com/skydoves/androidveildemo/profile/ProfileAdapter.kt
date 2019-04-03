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
import com.skydoves.androidveildemo.R
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ProfileAdapter(private val delegate: ProfileViewHolder.Delegate) : BaseAdapter() {

  init {
    addSection(ArrayList<Profile>())
  }

  fun addProfiles(profiles: List<Profile>) {
    addItemListOnSection(0, profiles)
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow): Int {
    return R.layout.item_profile
  }

  override fun viewHolder(layout: Int, view: View): BaseViewHolder {
    return ProfileViewHolder(view, delegate)
  }
}
