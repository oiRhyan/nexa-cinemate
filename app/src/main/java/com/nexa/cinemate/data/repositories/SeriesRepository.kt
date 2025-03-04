package com.nexa.cinemate.data.repositories

import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.models.response.image.ImageResponse
import com.nexa.cinemate.data.utils.API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class SeriesRepository(
    private val service : HttpClient
) {

     suspend fun getSeries() : MovieResponse {
         val response : HttpResponse = service.get("https://api.themoviedb.org/3/trending/tv/week?") {
             headers.append("Authorization", "Bearer $API_KEY")
             parameter("language", "pt-BR")
         }

         if(response.status == HttpStatusCode.OK) {
             return response.body()
         } else {
             throw Exception("Erro ao recupeara séries ${response.status}")
         }
     }

    suspend fun getSeriesRecomendeds() : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=pt-BR") {
            headers.append("Authorization", "Bearer $API_KEY")
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recupeara séries atuais ${response.status}")
        }
    }

    suspend fun getPopularSeries() : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/tv/top_rated?language=pt-BR&page=1") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
            parameter("page", 1)
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recupeara séries populares ${response.status}")
        }
    }

    suspend fun getSeriesImages(id : Int) : ImageResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/tv/$id/images") {
            headers.append("Authorization", "Bearer $API_KEY")
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recupearar imagens da série ${response.status}")
        }
    }




}