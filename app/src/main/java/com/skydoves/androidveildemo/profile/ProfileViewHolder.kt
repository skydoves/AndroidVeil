package com.skydoves.androidveildemo.profile

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_profile.view.*

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ProfileViewHolder(private val view: View,
                        private val delegate: Delegate) : BaseViewHolder(view) {

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
