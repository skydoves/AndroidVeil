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
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.androidveil.VeiledItemOnClickListener
import com.skydoves.androidveildemo.databinding.ActivityCarouselBinding
import com.skydoves.androidveildemo.profile.ListItemUtils
import com.skydoves.androidveildemo.profile.Profile
import com.skydoves.androidveildemo.profile.ProfileCarouselAdapter

class CarouselActivity :
  AppCompatActivity(),
  VeiledItemOnClickListener,
  ProfileCarouselAdapter.ProfileViewHolder.Delegate {

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityCarouselBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    val adapter = ProfileCarouselAdapter(this)
    // sets VeilRecyclerView's properties
    binding.veilRecyclerView.run {
      setVeilLayout(
        layout = R.layout.item_prepared_shimmer_carousel,
        isPrepared = true,
        onItemClickListener = this@CarouselActivity
      )
      setAdapter(adapter)
      setLayoutManager(LinearLayoutManager(this@CarouselActivity, RecyclerView.HORIZONTAL, false))
      addVeiledItems(10)
    }

    binding.veilRecyclerView2.run {
      setVeilLayout(
        layout = R.layout.item_prepared_shimmer_carousel,
        isPrepared = true,
        onItemClickListener = this@CarouselActivity
      )
      setAdapter(adapter)
      setLayoutManager(LinearLayoutManager(this@CarouselActivity, RecyclerView.HORIZONTAL, false))
      addVeiledItems(10)
    }

    // add profile times to adapter
    adapter.addProfiles(ListItemUtils.getProfiles(this))

    // delay-auto-unveil
    Handler(Looper.getMainLooper()).postDelayed(
      {
        binding.veilRecyclerView.unVeil()
      },
      3000
    )
    Handler(Looper.getMainLooper()).postDelayed(
      {
        binding.veilRecyclerView2.unVeil()
      },
      5000
    )
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
