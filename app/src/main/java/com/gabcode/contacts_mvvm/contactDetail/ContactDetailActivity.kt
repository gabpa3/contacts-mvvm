package com.gabcode.contacts_mvvm.contactDetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gabcode.contacts_mvvm.R
import com.gabcode.contacts_mvvm.data.model.Contact
import com.gabcode.contacts_mvvm.databinding.ActivityContactDetailBinding
import com.gabcode.contacts_mvvm.util.setupToolbar

class ContactDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER_ID: String = "userId"
    }

    private lateinit var contactDetailBinding: ActivityContactDetailBinding
    private lateinit var contactDetailViewModel: ContactDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val userId: String = bundle?.getString(EXTRA_USER_ID)!!

        contactDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail)
        contactDetailViewModel = obtainViewModel()
        contactDetailBinding.viewModel = contactDetailViewModel
        contactDetailBinding.layoutNoConnection?.listener = contactDetailViewModel

        setupToolbar(contactDetailBinding.layoutToolbar?.toolbar!!) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.title_contact_detail)
        }

        contactDetailViewModel.loadContact(userId).observe(this, Observer {it?.let {this.displayData(it)}})
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun obtainViewModel(): ContactDetailViewModel =
            ViewModelProviders.of(this).get(ContactDetailViewModel::class.java)

    private fun displayData(contact: Contact) {
        contactDetailBinding.data = contact
    }
}
