package com.yly.remotebinder

import android.content.Intent

class FileLocker {

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun init()

    external fun fileLockerOrigin(originPath: String, daemonPath: String)

    external fun fileLockerDaemon(originPath: String, daemonPath: String)

    fun deamonDead() {
        println("deamon dead")
        MyApplication.application.startService(
            Intent(
                MyApplication.application,
                RemoteService::class.java
            )
        )
        MyApplication.application.startService(
            Intent(
                MyApplication.application,
                RemoteService2::class.java
            )
        )
    }

    fun originDead() {
        println("origin dead")
        MyApplication.application.startService(
            Intent(
                MyApplication.application,
                RemoteService::class.java
            )
        )
        MyApplication.application.startService(
            Intent(
                MyApplication.application,
                RemoteService2::class.java
            )
        )
    }
}