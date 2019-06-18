package com.skydoves.androidveildemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.skydoves.androidveil.VeiledItemOnClickListener
import com.skydoves.androidveildemo.profile.ListItemUtils
import com.skydoves.androidveildemo.profile.Profile
import com.skydoves.androidveildemo.profile.ProfileAdapter
import com.skydoves.androidveildemo.profile.ProfileViewHolder
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), VeiledItemOnClickListener,
  ProfileViewHolder.Delegate {

  private val adapter by lazy { ProfileAdapter(this) }

  @SuppressLint("CheckResult")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)

    // sets VeilRecyclerView's properties
    veilFrameView.setVeilLayout(R.layout.item_preview, this)
    veilFrameView.setAdapter(adapter)
    veilFrameView.setLayoutManager(GridLayoutManager(this, 2))
    veilFrameView.addVeiledItems(12)

    // add profile times to adapter
    adapter.addProfiles(ListItemUtils.getProfiles(this))
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
