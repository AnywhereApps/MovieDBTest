package com.anywhereapps.moviedb.test.data.api

import com.anywhereapps.moviedb.test.data.model.DetailData
import com.anywhereapps.moviedb.test.data.model.MovieData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/movie/top_rated?")
    suspend fun getTopRatedMovieList(@Query("api_key") token : String, @Query("language") language : String, @Query("page") page : Int): MovieData


    @GET("/3/movie/{movieId}}")
    suspend fun getMovieDetail(@Path("movieId") movieId : Int , @Query("api_key") token : String, @Query("language") language : String): DetailData

}