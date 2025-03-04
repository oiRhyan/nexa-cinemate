package com.nexa.cinemate.screens.ui.views.anime

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.repositories.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animes = MutableStateFlow<List<Movie>>(emptyList())
    val animes = _animes.asStateFlow()

    private val _popularAnimes = MutableStateFlow<List<Movie>>(emptyList())
    val popularAnimes = _popularAnimes.asStateFlow()

    fun getAnimes() {
        viewModelScope.launch {
            try {
                val response = repository.getAnimes()
                _animes.value = response.results
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getPopularAnimes() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularAnimes()
                _popularAnimes.value = response.results
            } catch (e : Exception) {
                Log.e("Error connection", "${e.message}")
            }
        }
    }


}