package com.gabcode.contacts_mvvm.util

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CircularProgressDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gabcode.contacts_mvvm.R

fun setOnHolder(view: ImageView, imageUrl: String) =
    Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions()
                    .placeholder(getCircularProgress(view))
                    .error(R.drawable.photo_profile))
            .into(view)

fun setOnHolderLarge(view: ImageView, imageUrl: String?) =
        Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions()
                        .placeholder(getCircularProgress(view))
                        .centerCrop()
                        .error(R.drawable.photo_profile))
                .into(view)

fun getCircularProgress(view: ImageView): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.colorFilter =
            PorterDuffColorFilter(ContextCompat.getColor(view.context, R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP)
    circularProgressDrawable.start()
    return circularProgressDrawable

}