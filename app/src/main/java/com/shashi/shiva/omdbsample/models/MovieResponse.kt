package com.shashi.shiva.omdbsample.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "movies"
)
data class MovieResponse(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("Actors")
    val actors: String?,
    @SerializedName("Awards")
    val awards: String?,
    @SerializedName("BoxOffice")
    val boxOffice: String?,
    @SerializedName("Country")
    val country: String?,
    @SerializedName("DVD")
    val dVD: String?,
    @SerializedName("Director")
    val director: String?,
    @SerializedName("Genre")
    val genre: String?,
    @SerializedName("imdbID")
    val imdbID: String?,
    @SerializedName("imdbRating")
    val imdbRating: String?,
    @SerializedName("imdbVotes")
    val imdbVotes: String?,
    @SerializedName("Language")
    val language: String?,
    @SerializedName("Metascore")
    val metascore: String?,
    @SerializedName("Plot")
    val plot: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Production")
    val production: String?,
    @SerializedName("Rated")
    val rated: String?,
    @SerializedName("Released")
    val released: String?,
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Runtime")
    val runtime: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Website")
    val website: String?,
    @SerializedName("Writer")
    val writer: String?,
    @SerializedName("Year")
    val year: String?
): Serializable // by declaring Serializable, notifying kotlin that this(MovieResponse) can be passed between fragments.
