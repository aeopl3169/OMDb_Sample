package com.shashi.shiva.omdbsample.repository

import com.shashi.shiva.omdbsample.api.RetrofitInstance
import com.shashi.shiva.omdbsample.db.MoviesDatabase
import com.shashi.shiva.omdbsample.models.MovieResponse

class MovieRepository(
    val db: MoviesDatabase
) {
    // Search movies
    suspend fun searchMovies(searchQuery: String) =
        RetrofitInstance.api.searchMovies(searchQuery)

    // insert or update movie into DB.
    suspend fun upsert(movie: MovieResponse) = db.getMoviesDao().upsert(movie)

    // get all movies from DB.
    fun getSavedMovies() = db.getMoviesDao().getAllSavedMovies()

    // delete movie record form DB.
    suspend fun deleteSavedMovie(movie: MovieResponse) = db.getMoviesDao().deleteSavedMovie(movie)
}