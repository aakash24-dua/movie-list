package com.nationalconclave.bseb.ui.main.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AssignedDriver (

    @SerializedName("name")
    val name : String? = null,

    @SerializedName("phone_no")
    val phoneNo : String? = null,
    @SerializedName("car_no")
    val carNo : String? = null,
    @SerializedName("id")
    val id : String
        ) :Parcelable