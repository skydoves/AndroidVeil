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

package com.skydoves.androidveildemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.androidveil.VeiledItemOnClickListener
import com.skydoves.androidveildemo.databinding.ActivityMainBinding
import com.skydoves.androidveildemo.profile.ListItemUtils
import com.skydoves.androidveildemo.profile.Profile
import com.skydoves.androidveildemo.profile.ProfileAdapter


/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */
class MainActivity :
  AppCompatActivity(),
  VeiledItemOnClickListener,
  ProfileAdapter.ProfileViewHolder.Delegate {

  private val adapter = ProfileAdapter(this)
  private var isFloatingMenuOpen = false
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupFloatingActionButtons()

    // sets VeilRecyclerView's properties
    binding.veilRecyclerView.run {
      setVeilLayout(
        layout = R.layout.item_profile_list,
        onItemClickListener = this@MainActivity
      )
      setAdapter(adapter)
      setLayoutManager(LinearLayoutManager(this@MainActivity))
      addVeiledItems(15)
    }

    // add profile times to adapter
    adapter.addProfiles(ListItemUtils.getProfiles(this))

    // delay-auto-unveil
    Handler(Looper.getMainLooper()).postDelayed(
      {
        binding.veilRecyclerView.unVeil()
      },
      5000
    )
  }

  private fun setupFloatingActionButtons() {
    binding.fab.setOnClickListener {
      if (!isFloatingMenuOpen) {
        showFloatingMenu()
      } else {
        closeFloatingMenu()
      }
      }
    binding.fabGrid.setOnClickListener {
      startActivity(Intent(this, GridActivity::class.java))
      }
    binding.fabCarousel.setOnClickListener {
      startActivity(Intent(this, CarouselActivity::class.java))
      }
  }

  override fun onBackPressed() {
    if (isFloatingMenuOpen) {
      closeFloatingMenu()
    } else {
      super.onBackPressed()
    }
  }

  private fun showFloatingMenu() {
    isFloatingMenuOpen = true
    binding.fabCarousel.animate().translationY(-resources.getDimension(R.dimen.distance_fab_first))
    binding.fabGrid.animate().translationY(-resources.getDimension(R.dimen.distance_fab_second))
  }

  private fun closeFloatingMenu() {
    isFloatingMenuOpen = false;
    binding.fabCarousel.animate().translationY(0f);
    binding.fabGrid.animate().translationY(0f);
  }

  /** OnItemClickListener by Veiled Item */
  override fun onItemClicked(pos: Int) {
    Toast.makeText(this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show()
  }

  /** OnItemClickListener by User Item */
  override fun onItemClickListener(profile: Profile) {
    startActivity(Intent(this, DetailActivity::class.java))
  }
}
