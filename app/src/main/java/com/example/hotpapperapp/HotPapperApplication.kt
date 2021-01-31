package com.example.hotpapperapp

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree




class HotPapperApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
    }
}