package com.yly.remotebinder

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.File

class RemoteService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("RemoteService onCreate")
        val lockerFile = File(cacheDir, "locker")
        val originFile = File(lockerFile, "origin.txt")
        val deamonFile = File(lockerFile, "daemon.txt")
        FileLocker().fileLockerOrigin(originFile.absolutePath, deamonFile.absolutePath)
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onBind(intent: Intent?): IBinder? {
        return MyRemoteBinder()
    }
}