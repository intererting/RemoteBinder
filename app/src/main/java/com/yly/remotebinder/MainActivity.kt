package com.yly.remotebinder

import android.app.ActivityManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.os.Process
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lockerFile = File(cacheDir, "locker")
        if (!lockerFile.exists()) {
            lockerFile.mkdirs()
        }
        testService.setOnClickListener {
            testService()
        }

        startOriginService.setOnClickListener {
            startService(Intent(this@MainActivity, RemoteService::class.java))
//            startService(Intent(this@MainActivity, RemoteService3::class.java))
        }

        startDaemonService.setOnClickListener {
            startService(Intent(this@MainActivity, RemoteService2::class.java))
        }

        killProcess.setOnClickListener {
            Process.killProcess(process.text.trim().toString().toInt())
        }
    }

    external fun stringFromJNI(): String

    fun testService() {
        bindService(
            Intent(this@MainActivity, RemoteService::class.java),
            object : ServiceConnection {
                override fun onServiceDisconnected(name: ComponentName?) {
                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                    val data = Parcel.obtain()
                    val reply = Parcel.obtain()
                    data.writeInterfaceToken("MyRemoteBinder")
                    data.writeString("yuliyang")
                    service.transact(1000, data, reply, 0)
                    println(reply.readString())
                    data.recycle()
                    reply.recycle()
                }
            }, Context.BIND_AUTO_CREATE
        )
    }
}
