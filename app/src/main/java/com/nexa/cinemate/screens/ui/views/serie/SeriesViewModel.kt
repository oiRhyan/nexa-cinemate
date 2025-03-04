package com.nexa.cinemate.screens.ui.views.serie

import android.media.Image
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.models.response.image.Backdrop
import com.nexa.cinemate.data.repositories.SeriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SeriesViewModel(
    private val repository: SeriesRepository
) : ViewModel() {

    private val _series = MutableStateFlow<List<Movie>>(emptyList())
    val series = _series.asStateFlow()

    private val _seriesRecomends = MutableStateFlow<List<Movie>>(emptyList())
    val seriesRecomends = _seriesRecomends.asStateFlow()

    private val _seriesPopular = MutableStateFlow<List<Movie>>(emptyList())
    val seriesPopular = _seriesPopular.asStateFlow()

    private val _serieImages = MutableStateFlow<List<Backdrop>>(emptyList())
    val serieImages = _serieImages.asStateFlow()

    fun getSeries() {
        viewModelScope.launch {
            try {
                val response = repository.getSeries()
                _series.value =  response.results
            } catch (e : Exception)  {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getSeriesRecomendeds() {
        viewModelScope.launch {
            try {
                val response = repository.getSeriesRecomendeds()
                _seriesRecomends.value =  response.results
            } catch (e : Exception)  {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getPopularSeries() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularSeries()
                _seriesPopular.value =  response.results
            } catch (e : Exception)  {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

    fun getSerieImage(id : Int) {
        viewModelScope.launch {
            try {
                val response = repository.getSeriesImages(id)
                _serieImages.value = response.backdrops!!
            } catch (e : Exception)  {
                Log.e("Error connection", "${e.message}")
            }
        }
    }

}