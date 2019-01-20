package com.skydoves.androidveildemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.androidveil.visible
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.concurrent.TimeUnit

/**
 * Developed by skydoves on 2018-10-30.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@SuppressLint("CheckResult")
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        applyToolbarMargin(detail_toolbar)

        detail_veilLayout_header.shimmer = ShimmerUtils.getGrayShimmer(this)
        detail_veilLayout_body.shimmer = ShimmerUtils.getGrayShimmer(this)

        // delay-auto-unveil
        Observable.just(0).delay(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    detail_veilLayout_header.unVeil()
                    detail_veilLayout_body.unVeil()
                    linearLayout.visible()
                }
    }
}
