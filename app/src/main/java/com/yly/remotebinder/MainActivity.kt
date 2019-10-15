package com.yly.remotebinder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testService.setOnClickListener {
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
                        data.recycle()
                        reply.recycle()
                    }
                }, Context.BIND_AUTO_CREATE
            )
        }
    }
}
