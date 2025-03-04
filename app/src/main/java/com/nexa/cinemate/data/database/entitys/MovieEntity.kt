package com.nexa.cinemate.data.database.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    val mediaID : Int,
    val isFavorite : Boolean,
    val name : String? = "",
    val backdrop_path: String? =  null,
    val id: Int? = 0,
    val media_type : String? = "",
    val original_language: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val title: String? = "",
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0

)