package com.gabcode.contacts_mvvm

import android.app.Application
import com.gabcode.contacts_mvvm.data.source.remote.NetworkMonitor

class ContactsMvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkMonitor.initialize(this)
    }
}