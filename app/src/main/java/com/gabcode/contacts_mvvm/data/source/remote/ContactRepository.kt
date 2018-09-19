package com.gabcode.contacts_mvvm.data.source.remote

import com.gabcode.contacts_mvvm.data.model.Contact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class ContactRepository : ContactDataSource {

//    override fun getContacts(callback: ContactDataSource.LoadDataCallback<List<Contact>>) {
//        val call: Call<List<Contact>> = RestClient.getInstance()?.getApiService()!!.getContacts()
//        call.enqueue(object : Callback<List<Contact>> {
//            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
//                callback.onDataNotAvailable(t.message)
//            }
//
//            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
//                callback.onDataLoaded(response?.body()!!)
//            }
//        })
//    }

    override fun getContacts(callback: ContactDataSource.LoadDataCallback<List<Contact>>) {
        val call: Call<List<Contact>> = RestClient.getInstance()?.getApiService()!!.getContacts()
        call.enqueue(genericCallback(
                { s -> callback.onDataLoaded(s?.body()!!) },
                { f -> callback.onDataNotAvailable(f.message) }))
    }

    override fun getContact(id: String, callback: ContactDataSource.LoadDataCallback<Contact>) {
        val call: Call<Contact> = RestClient.getInstance()?.getApiService()!!.getContact(id)
        call.enqueue(genericCallback(
                { s -> callback.onDataLoaded(s?.body()!!) },
                { f -> callback.onDataNotAvailable(f.message) }))
    }

    private fun<T> genericCallback(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                if (response?.code() == HttpsURLConnection.HTTP_OK) success?.invoke(response!!)
            }
            override fun onFailure(call: Call<T>?, t: Throwable?) { failure?.invoke(t!!) }
        }
    }
}

