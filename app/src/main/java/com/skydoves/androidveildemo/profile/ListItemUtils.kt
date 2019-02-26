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

import android.content.Context
import androidx.core.content.ContextCompat
import com.skydoves.androidveildemo.R

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object ListItemUtils {
  fun getProfiles(context: Context): List<Profile> {
    val list = ArrayList<Profile>()
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person0), "The Little Prince", "And now here is my secret, a very simple secret: It is only with the heart that one can see rightly; what is essential is invisible to the eye."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person1), "Mia Vance", "All grown-ups were once children... but only few of them remember it."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person2), "Ryker Beil", "What makes the desert beautiful,' said the little prince, 'is that somewhere it hides a well..."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person3), "Kayden Bautista", "It is the time you have wasted for your rose that makes your rose so important."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person4), "Skylar Odom", "The most beautiful things in the world cannot be seen or touched, they are felt with the heart."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person5), "Autumn Villegas", "It is such a mysterious place, the land of tears."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person6), "Wyatt Sherman", "Well, I must endure the presence of a few caterpillars if I wish to become acquainted with the butterflies."))
    list.add(Profile(ContextCompat.getDrawable(context, R.drawable.person7), "Rachel Alvarado", "You see, one loves the sunset when one is so sad."))
    return list
  }
}
