package com.shashi.shiva.omdbsample.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shashi.shiva.omdbsample.MovieApplication
import com.shashi.shiva.omdbsample.models.MovieResponse
import com.shashi.shiva.omdbsample.repository.MovieRepository
import com.shashi.shiva.omdbsample.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val movieRepository: MovieRepository
) : AndroidViewModel(app) {

    val searchMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()

    fun searchMovies(searchQuery: String) = viewModelScope.launch {
        safeSearchMoviesCall(searchQuery)
    }

    // checking internet
    private suspend fun safeSearchMoviesCall(searchQuery: String) {
        searchMovies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = movieRepository.searchMovies(searchQuery)
                searchMovies.postValue(Resource.Success(response.body()))
            } else {
                searchMovies.postValue(Resource.Error("No internet"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchMovies.postValue(Resource.Error("Network Failure"))
                else -> searchMovies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    // save movie in DB.
    fun saveMovie(movie: MovieResponse) = viewModelScope.launch {
        movieRepository.upsert(movie)
    }

    // get all saved movies from DB.
    fun getAllSavedMovies() = movieRepository.getSavedMovies()

    fun deleteSavedMovie(movie: MovieResponse) = viewModelScope.launch {
        movieRepository.deleteSavedMovie(movie)
    }

    // Check internet connection
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MovieApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo.run {
                return when (this!!.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
}