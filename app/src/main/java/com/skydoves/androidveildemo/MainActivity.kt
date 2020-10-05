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

package com.skydoves.androidveildemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.androidveil.VeiledItemOnClickListener
import com.skydoves.androidveildemo.profile.ListItemUtils
import com.skydoves.androidveildemo.profile.Profile
import com.skydoves.androidveildemo.profile.ProfileAdapter
import com.skydoves.androidveildemo.profile.ProfileViewHolder
import kotlinx.android.synthetic.main.activity_main.veilRecyclerView

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity :
  AppCompatActivity(),
  VeiledItemOnClickListener,
  ProfileViewHolder.Delegate {

  private val adapter = ProfileAdapter(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // sets VeilRecyclerView's properties
    veilRecyclerView.run {
      setVeilLayout(R.layout.item_profile, this@MainActivity)
      setAdapter(adapter)
      setLayoutManager(LinearLayoutManager(this@MainActivity))
      addVeiledItems(15)
    }

    // add profile times to adapter
    adapter.addProfiles(ListItemUtils.getProfiles(this))

    // delay-auto-unveil
    Handler().postDelayed(
      {
        veilRecyclerView.unVeil()
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
