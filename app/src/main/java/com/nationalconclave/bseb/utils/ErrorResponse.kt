package com.nationalconclave.bseb.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorResponse (
    @SerializedName("statusCode")
    val statusCode : Int,
    @SerializedName("message")
    val message : String

        ) : Parcelable