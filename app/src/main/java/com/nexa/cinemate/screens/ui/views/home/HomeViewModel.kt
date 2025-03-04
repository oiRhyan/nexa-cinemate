package com.nexa.cinemate.screens.ui.views.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.models.response.details.Genre
import com.nexa.cinemate.data.models.response.image.Backdrop
import com.nexa.cinemate.data.repositories.MovieRepository
import com.nexa.cinemate.data.utils.toMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _relatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val relateMovies = _relatedMovies.asStateFlow()

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies = _favoriteMovies.asStateFlow()

    private val _relatedSeries = MutableStateFlow<List<Movie>>(emptyList())
    val relateSeries = _relatedSeries.asStateFlow()

    private val _images = MutableStateFlow<List<Backdrop>>(emptyList())
    val images = _images.asStateFlow()

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies = _upcomingMovies.asStateFlow()

    private val _searchMovieResult = MutableStateFlow<List<Movie>>(emptyList())
    val searchMovie = _searchMovieResult.asStateFlow()

    private val _details = MutableStateFlow<List<Genre>>(emptyList())
    val details = _details.asStateFlow()

    private val _date = MutableStateFlow<String>("")
    val date = _date.asStateFlow()

    var selectedMovie : Movie? = null

    fun setFavoriteMovie(movie : Movie) {
        viewModelScope.launch {
            movieRepository.setFavoriteMovie(movie)
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            movieRepository.favoriteMovies.collect { movieEntities ->
                _favoriteMovies.value = movieEntities.map { it.toMovie() }
            }
        }
    }

    fun getDetails(id : Int) {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMediaDetails(id)
                if(response == null) {
                    val response2 = movieRepository.getSerieDetails(id)
                    _details.value = response2.genres
                    _date.value = response2.release_date.take(4)
                } else {
                    _details.value = response.genres
                    _date.value = response.release_date.take(4)
                }
                Log.v("generos", response.genres.toString())

            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getMovies() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMovies()
                _movies.value = response.results
            } catch (e : Exception) {
                Log.e("Error Connection", "${e.message}")
            }
        }
    }

    fun searchMidias(search : String) {
        viewModelScope.launch {
            try {
                val responseMovie = movieRepository.searchMovie(search)
                val responseSeries = movieRepository.searchSeries(search)
                _searchMovieResult.value = responseMovie.results + responseSeries.results
                Log.v("Midia Search", searchMovie.value.toString())
            } catch (e : Exception) {
                Log.e("Error Connection", "${e.message}")
            }
        }
    }

    fun getRelatedMovies() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getRelateMovies()
                _relatedMovies.value = response.results
            } catch (e : Exception) {
                Log.e("Error Connection", "${e.message}")
            }
        }
    }

    fun getPopularMoveis() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies()
                _popularMovies.value = response.results
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getUpcomingMovies()
                _upcomingMovies.value = response.results
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getMidiaMovies(id : Int) {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMidiaImages(id)
                _images.value = response?.backdrops!!
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getRecomendationMovies(id : Int) {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMovieRecomendations(id)
                _relatedMovies.value = response.results
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getRecomendationSeries(id : Int) {
        viewModelScope.launch {
            try {
                val response = movieRepository.getSeriesRecomendeds(id)
                _relatedSeries.value = response.results
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

}