package com.nexa.cinemate.data.repositories

import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.utils.API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class AnimeRepository(
    private val service : HttpClient
) {

    suspend fun getAnimes() : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&page=1&sort_by=popularity.desc&with_genres=16&with_original_language=ja") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recupear animes ${response.status}")
        }
    }

    suspend fun getPopularAnimes() : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=pt-BR&page=1&sort_by=vote_count.desc&with_genres=16") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recupeara animes populares ${response.status}")
        }
    }

}