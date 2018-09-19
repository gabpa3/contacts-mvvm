package com.gabcode.contacts_mvvm.util

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

fun AppCompatActivity.setupToolbar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.run { action() }
    toolbar.navigationIcon?.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
}