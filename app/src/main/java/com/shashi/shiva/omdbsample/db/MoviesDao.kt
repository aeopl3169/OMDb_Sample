package com.shashi.shiva.omdbsample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shashi.shiva.omdbsample.models.MovieResponse

@Dao
interface MoviesDao {
    // insert or update movie into DB.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: MovieResponse): Long

    @Query("SELECT * FROM movies")
    fun getAllSavedMovies(): LiveData<List<MovieResponse>>

    @Delete
    suspend fun deleteSavedMovie(movie: MovieResponse)
}