package com.nexa.cinemate.data.models.response

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val adult: Boolean? = false,
    val name : String? = "",
    val backdrop_path: String? =  null,
    val genre_ids: List<Int>? = emptyList(),
    val id: Int? = 0,
    val media_type : String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val poster_path: String? = "",
    val release_date: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
)
