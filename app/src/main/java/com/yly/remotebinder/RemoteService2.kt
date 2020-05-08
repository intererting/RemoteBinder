package com.yly.remotebinder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.File

class RemoteService2 : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val lockerFile = File(cacheDir, "locker")
        val originFile = File(lockerFile, "origin.txt")
        val deamonFile = File(lockerFile, "daemon.txt")
        println("RemoteService2 create")
        FileLocker().fileLockerDaemon(originFile.absolutePath, deamonFile.absolutePath)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}