package com.example.bitable_fe.data

import android.util.Log
import timber.log.Timber

class ReleaseLogTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            Log.println(priority, tag, message)
        }
    }
}
