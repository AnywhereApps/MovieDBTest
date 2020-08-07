package com.anywhereapps.moviedb.test.data.model


import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
) : Parcelable