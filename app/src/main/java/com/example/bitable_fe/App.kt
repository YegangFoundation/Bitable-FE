package com.example.bitable_fe

import android.app.Application
import com.example.bitable_fe.data.ReleaseLogTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        setupLogging()
    }

    private fun setupLogging(){
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseLogTree())
        }
    }
}