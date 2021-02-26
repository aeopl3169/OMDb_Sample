package com.shashi.shiva.omdbsample.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shashi.shiva.omdbsample.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelProviderFactory(
    val app: Application,
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(app, movieRepository) as T
    }
}