package com.nexa.cinemate.data.repositories

import com.nexa.cinemate.data.database.dao.MovieDAO
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.models.response.details.MidiaDetailsResponse
import com.nexa.cinemate.data.models.response.image.ImageResponse
import com.nexa.cinemate.data.utils.API_KEY
import com.nexa.cinemate.data.utils.toMovieEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val service : HttpClient,
    private val storage : MovieDAO
) {

    val favoriteMovies get() = storage.findAllFavoriteMovies()

    suspend fun setFavoriteMovie(movie : Movie) {
        storage.insertFavoriteMovie(movie.toMovieEntity())
    }


    suspend fun getMovies() : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/movie/now_playing?language=pt-Br&page=1") {
            headers.append("Authorization", "Bearer $API_KEY")
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao obter filmes: ${response.status}")
        }
    }

    suspend fun getMediaDetails(id : Int) : MidiaDetailsResponse {
         val response : HttpResponse = service.get("https://api.themoviedb.org/3/movie/$id") {
             headers.append("Authorization", "Bearer $API_KEY")
             parameter("language", "pt-BR")
         }

         if(response.status == HttpStatusCode.OK) {
             return response.body()
         } else {
             throw Exception("Erro ao recuperar detalhes do filme ${response.status}")
         }
    }

    suspend fun getSerieDetails(id : Int) : MidiaDetailsResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/tv/$id") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recuperar detalhes do série ${response.status}")
        }
    }



    suspend fun getRelateMovies() : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/movie/top_rated?") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
            parameter("page", 1)
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        }
        else {
            throw Exception("Erro ao obter filmes relatados: ${response.status}")
        }
    }

    suspend fun getPopularMovies() : MovieResponse {
        val response : HttpResponse= service.get("https://api.themoviedb.org/3/movie/popular?") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
            parameter("page", 1)
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao obter filmes populares: ${response.status}")
        }
    }

    suspend fun getUpcomingMovies() : MovieResponse {
        val response : HttpResponse= service.get("https://api.themoviedb.org/3/movie/upcoming?") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
            parameter("page", 1)
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao obter filmes lançamentos: ${response.status}")
        }
    }


    suspend fun searchMovie(search : String) : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/search/movie?") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("query", search)
            parameter("include_adult", false)
            parameter("language", "pt-Br")
            parameter("page", 1)
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao pesquisar filmes: ${response.status}")
        }
    }

    suspend fun searchSeries(search : String) : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/search/tv?") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("query", search)
            parameter("include_adult", false)
            parameter("language", "pt-Br")
            parameter("page", 1)
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao pesquisar séries: ${response.status}")
        }
    }

    suspend fun getMidiaImages(id : Int) : ImageResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/movie/$id/images") {
            headers.append("Authorization", "Bearer $API_KEY")
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Error ao recuperar imagens: ${response.status}")
        }
    }

    suspend fun getMovieRecomendations(id : Int) : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/movie/$id/recommendations?language=en-US&page=1") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
            parameter("page", 1)
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recuperar recomendações ${response.status}")
        }
    }

    suspend fun getSeriesRecomendeds(id : Int) : MovieResponse {
        val response : HttpResponse = service.get("https://api.themoviedb.org/3/tv/$id/recommendations?") {
            headers.append("Authorization", "Bearer $API_KEY")
            parameter("language", "pt-BR")
            parameter("page", 1)
        }

        if(response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Erro ao recupeara séries atuais ${response.status}")
        }
    }



}