package com.nationalconclave.bseb.ui.main.registration

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationRequest (

    @SerializedName("first_name")
    val firstName : String? = null,
    @SerializedName("last_name")
    val lastName : String? = null,

    @SerializedName("organisation")
    val organisation : String? = null,
    @SerializedName("designation")
    val designation : String? = null,
    @SerializedName("email")
    val email : String? = null,
    @SerializedName("phone_no")
    val phoneNo : String? = null,
    @SerializedName("arrival_date")
    val arrivalDate : String? = null,

    @SerializedName("departure_date")
    val departureDate : String? = null,

    @SerializedName("arrival_flight_no")
    val arrivalFlightNo : String? = null,

    @SerializedName("departure_flight_no")
    val departureFlightNo : String? = null,
    @SerializedName("is_sight_selected")
    val isSightSelected : Boolean = false,
    @SerializedName("sight_seeing")
    var siteSeeing : List<String>? = null



        ) : Parcelable