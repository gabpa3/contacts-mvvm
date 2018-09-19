package com.gabcode.contacts_mvvm.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gabcode.contacts_mvvm.R
import com.gabcode.contacts_mvvm.data.model.Phone

@BindingAdapter("thumb")
fun loadImage(view: ImageView, imageUrl: String) = setOnHolder(view, imageUrl)

@BindingAdapter("image")
fun loadImageLarge(view: ImageView, imageUrl: String?) = setOnHolderLarge(view, imageUrl)

@BindingAdapter("phoneAvailable")
fun filterByFirstPhoneAvailable(text: TextView, phones: List<Phone>) {
    val phone: Phone? = phones.find { phone -> phone?.number != null }
    text.text = phone?.number ?: text.context.getText(R.string.message_no_phone)
}
