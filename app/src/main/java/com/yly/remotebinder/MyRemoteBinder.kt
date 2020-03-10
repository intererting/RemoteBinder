package com.yly.remotebinder

import android.os.Binder
import android.os.Parcel

class MyRemoteBinder : Binder() {

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when (code) {
            1000 -> {
                data.enforceInterface("MyRemoteBinder")
                println("data.readString()   ${data.readString()}")
                reply?.writeString("haha i have received")
                return true
            }
        }
        return super.onTransact(code, data, reply, flags)
    }
}