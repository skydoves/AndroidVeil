package com.skydoves.androidveildemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.androidveil.VeiledItemOnClickListener
import com.skydoves.androidveildemo.profile.ListItemUtils
import com.skydoves.androidveildemo.profile.Profile
import com.skydoves.androidveildemo.profile.ProfileAdapter
import com.skydoves.androidveildemo.profile.ProfileViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity : AppCompatActivity(), VeiledItemOnClickListener, ProfileViewHolder.Delegate {

    private val adapter by lazy { ProfileAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // sets VeilRecyclerView's properties
        veilRecyclerView.setVeilLayout(R.layout.item_profile, this)
        veilRecyclerView.setAdapter(adapter)
        veilRecyclerView.setLayoutManager(LinearLayoutManager(this))
        veilRecyclerView.addVeiledItems(15)

        // add profile times to adapter
        adapter.addProfiles(ListItemUtils.getProfiles(this))

        // delay-auto-unveil
        val delay = Observable.just(0).delay(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { veilRecyclerView.unVeil() }
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
