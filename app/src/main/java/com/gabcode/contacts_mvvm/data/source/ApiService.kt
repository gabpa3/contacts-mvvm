package com.gabcode.contacts_mvvm.data.source

import com.gabcode.contacts_mvvm.data.model.Contact
import com.gabcode.contacts_mvvm.data.source.remote.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constant.CONTACTS_ENDPOINT)
    fun getContacts(): Call<List<Contact>>

    @GET(Constant.CONTACT_ENDPOINT)
    fun getContact(@Path("userId") userId: String): Call<Contact>
}