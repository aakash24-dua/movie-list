package com.nationalconclave.bseb.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class GenericResponse<T> (
    @SerializedName("data")
    val data : T,
    @SerializedName("success")
    val isSuccess : Boolean,
    @SerializedName("error")
    val error : ErrorResponse? = null


)