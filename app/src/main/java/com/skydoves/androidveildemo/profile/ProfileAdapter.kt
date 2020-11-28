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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.androidveildemo.databinding.ItemProfileBinding

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ProfileAdapter(
  private val delegate: ProfileViewHolder.Delegate
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

  private val profileList: MutableList<Profile> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
    val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ProfileViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
    val profileItem = profileList[position]
    holder.binding.apply {
      profileItem.image?.let { profile.setImageDrawable(it) }
      name.text = profileItem.name
      content.text = profileItem.content
      root.setOnClickListener { delegate.onItemClickListener(profileItem) }
    }
  }

  fun addProfiles(profiles: List<Profile>) {
    this.profileList.addAll(profiles)
    notifyDataSetChanged()
  }

  override fun getItemCount() = this.profileList.size

  class ProfileViewHolder(val binding: ItemProfileBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun interface Delegate {
      fun onItemClickListener(profile: Profile)
    }
  }
}
