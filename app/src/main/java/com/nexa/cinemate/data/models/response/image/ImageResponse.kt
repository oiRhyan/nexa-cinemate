package com.nexa.cinemate.data.models.response.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val backdrops: List<Backdrop>? = emptyList(),
    val id: Int? = 0,
    val logos: List<Logo>? = emptyList(),
    val posters: List<Poster>? = emptyList()
)