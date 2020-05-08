package com.yly.remotebinder

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.properties.Delegates

class MyApplication : Application() {

    companion object {
        var application: Application by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}