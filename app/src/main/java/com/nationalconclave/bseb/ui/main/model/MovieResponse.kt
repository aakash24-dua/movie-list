package com.nationalconclave.bseb.ui.main.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieResponse(
	@field:SerializedName("Search")
	val response: ArrayList<Movie?>? = null
):Parcelable
@Parcelize
data class Movie(

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Year")
	val year: String? = null,

	@field:SerializedName("imdbID")
	val imdbID: String? = null,

	@field:SerializedName("Poster")
	val poster: String? = null,

	@field:SerializedName("Title")
	val title: String? = null
):Parcelable
