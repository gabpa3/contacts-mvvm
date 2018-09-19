package com.gabcode.contacts_mvvm.data.model

import com.google.gson.annotations.SerializedName

data class Contact (
        @SerializedName("user_id") val id: String,
        @SerializedName("birth_date") val birthDate: String,
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        val phones: List<Phone>,
        val thumb: String,
        val photo: String,
        val addresses: List<Address>?
)