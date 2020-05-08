package com.yly.remotebinder

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RemoteService3 : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        for (i in 0..10000) {
            println("haha   $i")
            Thread.sleep(5000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
}