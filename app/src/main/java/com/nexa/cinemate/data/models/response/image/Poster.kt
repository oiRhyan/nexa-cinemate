package com.nexa.cinemate.data.models.response.image

import kotlinx.serialization.Serializable

@Serializable
data class Poster(
    val aspect_ratio: Double? = 0.0,
    val file_path: String? = "",
    val height: Int? = 0,
    val iso_639_1: String? = "",
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0,
    val width: Int? = 0
)