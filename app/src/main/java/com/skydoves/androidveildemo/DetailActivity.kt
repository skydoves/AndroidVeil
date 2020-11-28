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

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.androidveildemo.databinding.ActivityDetailBinding

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)
    applyToolbarMargin(binding.detailToolbar)

    binding.detailVeilLayoutHeader.shimmer = ShimmerUtils.getGrayShimmer(this)
    binding.detailVeilLayoutBody.shimmer = ShimmerUtils.getGrayShimmer(this)

    // delay-auto-unveil
    Handler(Looper.getMainLooper()).postDelayed(
      {
        binding.detailVeilLayoutHeader.unVeil()
        binding.detailVeilLayoutBody.unVeil()
      },
      3000
    )
  }
}
