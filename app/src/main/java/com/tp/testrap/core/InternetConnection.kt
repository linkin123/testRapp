package com.tp.testrap.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


enum class CurrentConn {
    NOT_INTERNET, IS_WIFI, IS_MOBILE
}

object InternetConnection{
        fun check(context: Context): CurrentConn {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
            if (connMgr != null) {
                val activeNetworkInfo = connMgr.activeNetworkInfo
                if (activeNetworkInfo != null) {
                    val isConnected = activeNetworkInfo.isConnectedOrConnecting
                    if (!isConnected) return CurrentConn.NOT_INTERNET
                    return if (connMgr.isActiveNetworkMetered) {
                        CurrentConn.IS_MOBILE
                    } else {
                        CurrentConn.IS_WIFI
                    }
                }
            }
            return CurrentConn.NOT_INTERNET
        }
}