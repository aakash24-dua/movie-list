package com.nationalconclave.bseb.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast


fun Context.showToast(message: String?) {
    Toast.makeText(this.applicationContext, message, Toast.LENGTH_LONG).show()
}

fun View?.show() {
    if (this == null)
        return
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View?.gone() {
    if (this == null)
        return
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View?.setVisible(show: Boolean) {
    if (this == null)
        return
    if (show) this.show() else this.gone()
}

fun View?.hide() {
    if (this == null)
        return
    if (this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}

fun View?.isVisible(): Boolean {
    if (this == null) return false
    return this.visibility == View.VISIBLE
}

fun String.isValidEmail() : Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return this.matches(emailPattern)
}

fun String.isValidPhoneNumber() : Boolean {
    return this.isNotBlank() && this.isNotEmpty() && this.length == 10
}

/**
 * Bundle class is highly optimized for marshalling and unmarshalling using parcels.
 * */
fun Intent.putParcelable(key: String, parcel: Parcelable) {
    val bundle = Bundle()
    bundle.putParcelable(key, parcel)
    this.putExtra(key, bundle)
}

fun <T : Parcelable> Intent.getParcelable(key: String): T? {
    return this.getBundleExtra(key)?.getParcelable(key)
}

