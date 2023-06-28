package com.nationalconclave.bseb.ui.main.otp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OtpVerifiedResponse(
    @SerializedName("token")
    val token : String

):Parcelable