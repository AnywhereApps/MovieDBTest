package com.anywhereapps.moviedb.test.data.model


import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Genre(
    val id: Int,
    val name: String
) : Parcelable