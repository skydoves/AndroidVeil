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

class ProfileAdapter(private val delegate: ProfileViewHolder.Delegate): BaseAdapter() {

    init {
        addSection(ArrayList<Profile>())
    }

    fun addProfiles(profiles: List<Profile>) {
        addItemsOnSection(0, profiles)
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_profile
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return ProfileViewHolder(view, delegate)
    }
}
