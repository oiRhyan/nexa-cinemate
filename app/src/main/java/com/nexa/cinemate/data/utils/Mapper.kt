package com.nexa.cinemate.data.utils

import com.nexa.cinemate.data.database.entitys.MovieEntity
import com.nexa.cinemate.data.models.response.Movie


fun Movie.toMovieEntity() = MovieEntity(
    name = this.name,
    mediaID = 0,
    isFavorite = false,
    backdrop_path = this.backdrop_path,
    id = this.id,
    media_type = this.media_type,
    original_language = this.original_language,
    overview = this.overview,
    poster_path = this.poster_path,
    title = this.title,
    vote_average = this.vote_average,
    vote_count = this.vote_count,
)

fun MovieEntity.toMovie() = Movie(
    name = this.name,
    backdrop_path = this.backdrop_path,
    id = this.id,
    media_type = this.media_type,
    original_language = this.original_language,
    overview = this.overview,
    poster_path = this.poster_path,
    title = this.title,
    vote_average = this.vote_average,
    vote_count = this.vote_count
)