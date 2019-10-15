package com.yly.remotebinder

import android.os.Binder
import android.os.Parcel

class MyRemoteBinder : Binder() {

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        data.enforceInterface("MyRemoteBinder")
        when (code) {
            1000 -> println("data.readString()   ${data.readString()}")
        }
        return super.onTransact(code, data, reply, flags)
    }
}