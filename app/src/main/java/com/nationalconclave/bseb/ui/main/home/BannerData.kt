package com.nationalconclave.bseb.ui.main.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerData (
    val bannerImage : Int,
    val bannerTitle : String,
    val bannerSubtitle : String,
    val bannerLink : String

        ) : Parcelable