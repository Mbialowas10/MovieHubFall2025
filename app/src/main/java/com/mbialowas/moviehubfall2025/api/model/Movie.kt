package com.mbialowas.moviehubfall2025.api.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "movies")
data class Movie(
    @Json(name = "adult")
    var adult: Boolean = false,

    @Json(name = "backdrop_path")
    var backdropPath: String = "",

    //@Json(name = "genre_ids")
    //var genreIds: List<Int> = emptyList(),
    @PrimaryKey
    @Json(name = "id")
    var id: Int = 0,

    @Json(name = "media_type")
    var mediaType: String = "",

    @Json(name = "original_language")
    var originalLanguage: String = "",

    @Json(name = "original_title")
    var originalTitle: String = "",

    @Json(name = "overview")
    var overview: String = "Default Overview",

    @Json(name = "popularity")
    var popularity: Double = 0.0,

    @Json(name = "poster_path")
    var posterPath: String = "Default Poster Path",

    @Json(name = "release_date")
    var releaseDate: String = "",

    @Json(name = "title")
    var title: String = "Default title",

    @Json(name = "video")
    var video: Boolean = false,

    @Json(name = "vote_average")
    var voteAverage: Double = 0.0,

    @Json(name = "vote_count")
    var voteCount: Int = 0
)