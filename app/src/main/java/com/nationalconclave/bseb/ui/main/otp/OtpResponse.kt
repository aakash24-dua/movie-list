package com.nationalconclave.bseb.ui.main.otp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OtpResponse (
    @SerializedName("user_id")
    val userId : String,

    @SerializedName("phone_no")
    val phoneNo : String,

    @SerializedName("expires_at")
    val expiresAt : String
        ) : Parcelable