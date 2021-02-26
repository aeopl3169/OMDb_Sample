package com.shashi.shiva.omdbsample.api

import com.shashi.shiva.omdbsample.models.MovieResponse
import com.shashi.shiva.omdbsample.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    // @GET(".") to declare that your final URL is the same as your base URL.
    @GET(".")
    suspend fun searchMovies(
        @Query("t")
        movieName: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<MovieResponse>
}