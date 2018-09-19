package com.gabcode.contacts_mvvm.contact

import android.app.Activity
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabcode.contacts_mvvm.R
import com.gabcode.contacts_mvvm.data.model.Contact
import com.gabcode.contacts_mvvm.databinding.ViewholderContactBinding
import android.support.v4.util.Pair

class ContactAdapter(private var contacts: List<Contact>, private val contactViewModel: ContactViewModel) :
        RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    fun setContactData(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactHolder(DataBindingUtil.inflate(layoutInflater, R.layout.viewholder_contact, parent, false), contactViewModel)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactHolder, position: Int) =
            holder.bind(contacts[position])

    class ContactHolder(private val binding: ViewholderContactBinding, private val contactViewModel: ContactViewModel) :
            RecyclerView.ViewHolder(binding.root), ContactItemListener{

        fun bind(contact: Contact) {
            binding.data = contact
            binding.listener = this
            binding.executePendingBindings()
        }

        override fun onItemClick(userId: String) {
            val pairImage: Pair<View, String> = Pair.create(binding.imgPhotoProfile, "transitionImage")
            val pairName: Pair<View, String> = Pair.create(binding.txFullName, "transitionName")
            val options: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(binding.root.context as Activity, pairImage, pairName)

            var shareData = ShareData(userId, options.toBundle()!!)
            contactViewModel.openContactEvent.value = shareData
        }
    }

    fun filterItemsByName(queryText: String?) {
        if (queryText?.isEmpty()!! && contactViewModel.loadContacts().value != null) {
            setContactData(contactViewModel.loadContacts().value!!)
        } else if (queryText.isNotEmpty() && contacts.isNotEmpty()) {
            val contactsFiltered = contacts.filter {
                val fullName = it.firstName + " " + it.lastName
                fullName.toLowerCase().contains(queryText.toLowerCase()!!)
            }
            if (contactsFiltered.isNotEmpty()) setContactData(contactsFiltered)
        }
    }
}