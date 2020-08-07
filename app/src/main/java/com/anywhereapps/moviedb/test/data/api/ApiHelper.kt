package com.anywhereapps.moviedb.test.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getMovies(
        token: String
    ) = apiService.getTopRatedMovieList(token, "en-US", 1)

    suspend fun getDetail(
        token: String, movieId : Int
    ) = apiService.getMovieDetail(movieId, token, "en-US")
}