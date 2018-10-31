package com.skydoves.androidveildemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.concurrent.TimeUnit

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        applyToolbarMargin(detail_toolbar)

        detail_veilLayout_header.shimmer = ShimmerUtils.getGrayShimmer(this)

        // delay-auto-unveil
        val delay = Observable.just(0).delay(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    detail_veilLayout_header.unVeil()
                    detail_veilLayout_body.unVeil()
                }
    }
}
