package com.gabcode.contacts_mvvm.data.source.remote

import com.gabcode.contacts_mvvm.BuildConfig
import com.gabcode.contacts_mvvm.data.source.ApiService
import com.gabcode.contacts_mvvm.data.source.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {

    companion object {
        private var mInstance: RestClient? = null

        fun getInstance(): RestClient? =
            if (mInstance == null) {
                mInstance = RestClient()
                mInstance
            } else
                mInstance
    }

    fun getApiService(): ApiService {
        val client: OkHttpClient =
                OkHttpClient().newBuilder()
                    .addInterceptor(NetworkInterceptor())
//                    .addInterceptor(HttpLoggingInterceptor().apply {
//                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
//                        else HttpLoggingInterceptor.Level.NONE
//                    })
                    .build()

        val gson = GsonBuilder().create()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constant.MOCK_SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(ApiService::class.java)
    }



}