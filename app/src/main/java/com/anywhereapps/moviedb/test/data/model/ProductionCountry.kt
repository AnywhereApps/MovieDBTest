package com.anywhereapps.moviedb.test.data.model


import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
) : Parcelable