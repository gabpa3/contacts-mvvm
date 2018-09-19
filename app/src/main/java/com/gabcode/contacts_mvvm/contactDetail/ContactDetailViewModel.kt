package com.gabcode.contacts_mvvm.contactDetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.util.Log
import com.gabcode.contacts_mvvm.data.model.Contact
import com.gabcode.contacts_mvvm.data.source.RetryListener
import com.gabcode.contacts_mvvm.data.source.remote.ContactDataSource
import com.gabcode.contacts_mvvm.data.source.remote.ContactRepository

class ContactDetailViewModel : ViewModel(), RetryListener {

    companion object {
       private val TAG: String = ContactDetailViewModel::class.java.simpleName
    }

    private var contactData: MutableLiveData<Contact>? = null
    private val contactRepository: ContactRepository = ContactRepository()

    private var userId: String? = null

    val loading: ObservableBoolean = ObservableBoolean(true)
    val noConnection: ObservableBoolean = ObservableBoolean(false)

    fun loadContact(userId: String): MutableLiveData<Contact> {
        if (contactData?.value == null && this.userId != userId) {
            this.userId = userId
            contactData = MutableLiveData()
            getContactData(userId)
        }

        return contactData!!
    }

    private fun getContactData(userId: String) {
        loading.set(true)
        if (noConnection.get()) noConnection.set(false)
        contactRepository.getContact(userId, object: ContactDataSource.LoadDataCallback<Contact> {
            override fun onDataLoaded(data: Contact) {
                loading.set(false)
                contactData?.value = data
            }

            override fun onDataNotAvailable(message: String?) {
                Log.e(TAG, message)
                loading.set(false)
                noConnection.set(true)
            }
        })
    }

    override fun onRetryClick() {
        userId?.let { getContactData(it) }
    }

}