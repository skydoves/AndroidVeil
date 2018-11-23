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
