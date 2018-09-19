package com.gabcode.contacts_mvvm.contact

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import com.gabcode.contacts_mvvm.R
import com.gabcode.contacts_mvvm.contactDetail.ContactDetailActivity
import com.gabcode.contacts_mvvm.data.model.Contact
import com.gabcode.contacts_mvvm.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity(), ContactItemNavigator, SearchView.OnQueryTextListener{

    private lateinit var contactBinding: ActivityContactBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactBinding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        contactViewModel = obtainViewModel()

        contactBinding.viewModel = contactViewModel
        setUpRecycler(contactBinding.recyclerView)
        setupSearchView(contactBinding.searchView)
        contactBinding.layoutNoConnection?.listener = contactViewModel

        contactViewModel.loadContacts().observe(this, Observer { it?.let { this.displayData(it) } })
        contactViewModel.openContactEvent.observe(this, Observer { it?.let { openContactDetail(it) } })
    }

    override fun onResume() {
        super.onResume()
        contactBinding.viewModel?.start()
    }

    private fun setUpRecycler(recyclerView: RecyclerView) {
        adapter = ContactAdapter(ArrayList(0), contactViewModel)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.isFocusable = true
        searchView.queryHint = "Search by name"
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filterItemsByName(newText)
        return false
    }

    fun obtainViewModel(): ContactViewModel =
            ViewModelProviders.of(this).get(ContactViewModel::class.java)

    private fun displayData(contacts: List<Contact>) = adapter.setContactData(contacts)

    override fun openContactDetail(shareData: ShareData) {
        val intent = Intent(this, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.EXTRA_USER_ID, shareData.userId)
        startActivity(intent, shareData.options)
    }


}
