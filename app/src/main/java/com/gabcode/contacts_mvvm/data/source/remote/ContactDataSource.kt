package com.gabcode.contacts_mvvm.data.source.remote

import com.gabcode.contacts_mvvm.data.model.Contact

interface ContactDataSource {

    interface LoadDataCallback<T> {
        fun onDataLoaded(data: T)
        fun onDataNotAvailable(message: String?)
    }

    fun getContacts(callback: LoadDataCallback<List<Contact>>)
    fun getContact(id: String, callback: LoadDataCallback<Contact>)

}