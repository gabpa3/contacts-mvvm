package com.gabcode.contacts_mvvm.data.source.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkMonitor(private val context: Context) : NetworkMonitorListener  {

    companion object {
        private var mInstance: NetworkMonitor ?= null

        fun initialize(context: Context){
            mInstance = NetworkMonitor(context)
        }

        fun getInstance(): NetworkMonitor? {
            return mInstance
        }
    }

    override fun isConnected(): Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}