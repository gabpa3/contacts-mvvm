package com.gabcode.contacts_mvvm.data.source.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.UnknownHostException

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request: Request = chain?.request()!!
        if (!NetworkMonitor.getInstance()!!.isConnected())
            throw UnknownHostException("NoNetWorkConnection " + request)
        else {
            request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
        }

        return chain.proceed(request)
    }
}