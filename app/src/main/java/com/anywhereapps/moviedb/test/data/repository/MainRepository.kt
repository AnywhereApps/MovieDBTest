package com.anywhereapps.moviedb.test.data.repository

import com.anywhereapps.moviedb.test.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getMovies(token: String
    ) = apiHelper.getMovies(token)


    suspend fun getDetail(token: String, id : Int
    ) = apiHelper.getDetail(token, id)


}