package com.nationalconclave.bseb.ui.main.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserResponse (


        @SerializedName("first_name")
        val firstName : String,
        @SerializedName("last_name")
        val lastName : String,

        @SerializedName("name")
        val name : String,

        @SerializedName("organisation")
        val organisation : String,
        @SerializedName("designation")
        val designation : String,
        @SerializedName("email")
        val email : String,
        @SerializedName("phone_no")
        val phoneNo : String,
        @SerializedName("arrival_date")
        val arrivalDate : String,

        @SerializedName("departure_date")
        val departureDate : String,
        @SerializedName("sight_seeing")
        val siteSeeing : List<String>,
        @SerializedName("_id")
        val id : String? = null,
        @SerializedName("created_at")
        val created : String?= null,
        @SerializedName("updated_at")
        val updated : String? = null,

        @SerializedName("is_sight_selected")
        val isSightSelected : Boolean = false,
        @SerializedName("__v")
        val v : String? = null,

        @SerializedName("assigned_officer")
        val assignedOfficer : AssignedOfficer ? = null,

        @SerializedName("assigned_driver")
        val assignedDriver : AssignedDriver? = null


        ) : Parcelable