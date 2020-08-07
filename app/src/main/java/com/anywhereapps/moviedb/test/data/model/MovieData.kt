package com.anywhereapps.moviedb.test.data.model


import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class MovieData(
    val page: Int,
    val results: List<Results>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable