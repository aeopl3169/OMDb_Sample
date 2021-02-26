package com.shashi.shiva.omdbsample.utils

import com.shashi.shiva.omdbsample.BuildConfig

class Constants {
    companion object{
        const val API_KEY = BuildConfig.SECRET_API_KEY
        const val BASE_URL = "http://www.omdbapi.com/"
        const val SEARCH_MOVIE_TIME_DELAY = 500L
    }
}