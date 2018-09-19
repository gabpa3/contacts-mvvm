package com.gabcode.contacts_mvvm.data.source.remote

interface NetworkMonitorListener {

    fun isConnected(): Boolean
}