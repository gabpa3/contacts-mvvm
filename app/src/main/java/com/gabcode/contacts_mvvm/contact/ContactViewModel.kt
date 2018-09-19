package com.gabcode.contacts_mvvm.contact

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.util.Log
import com.gabcode.contacts_mvvm.SingleLiveEvent
import com.gabcode.contacts_mvvm.data.model.Contact
import com.gabcode.contacts_mvvm.data.source.RetryListener
import com.gabcode.contacts_mvvm.data.source.remote.ContactDataSource
import com.gabcode.contacts_mvvm.data.source.remote.ContactRepository

class ContactViewModel : ViewModel(), RetryListener {

    companion object {
        private val TAG: String = ContactViewModel::class.java.simpleName
    }

    private var contactData: MutableLiveData<List<Contact>>?  = null
    private val contactRepository: ContactRepository = ContactRepository()

    internal var openContactEvent: SingleLiveEvent<ShareData> = SingleLiveEvent()
    val loading: ObservableBoolean = ObservableBoolean(false)
    val noConnection: ObservableBoolean = ObservableBoolean(false)
    val searchDisableEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun start() {

    }

    fun loadContacts(): LiveData<List<Contact>> {
        if (contactData?.value == null) {
            contactData = MutableLiveData()
            getContactsData()
        }

        return contactData!!
    }

    private fun getContactsData() {
        loading.set(true)
        if (noConnection.get()) noConnection.set(false)
        contactRepository.getContacts(object: ContactDataSource.LoadDataCallback<List<Contact>> {
            override fun onDataLoaded(data: List<Contact>) {
                loading.set(false)
                Log.d(TAG, loading.get().toString())
                contactData?.value = data
            }

            override fun onDataNotAvailable(message: String?) {
                Log.e(TAG, message)
                loading.set(false)
                noConnection.set(true)
                searchDisableEvent.value = true
            }
        })
    }

    override fun onRetryClick() = getContactsData()

}