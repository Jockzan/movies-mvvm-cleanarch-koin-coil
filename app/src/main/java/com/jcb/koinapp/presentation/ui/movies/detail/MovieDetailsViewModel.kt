package com.jcb.koinapp.presentation.ui.movies.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcb.koinapp.domain.models.MovieDetails
import com.jcb.koinapp.domain.models.Video
import com.jcb.koinapp.domain.repositories.DetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
    private val detailsRepository: DetailsRepository,
) : ViewModel() {

    var movieVideos by mutableStateOf<List<Video>>(listOf())
    var movieDetails by mutableStateOf<MovieDetails?>(null)

    fun getMovieDetails(movieId: Int = 1) = viewModelScope.launch {
        val details = withContext(Dispatchers.IO) {
            detailsRepository
                .getMovieDetails(movieId)
        }
        movieDetails = details
    }

    fun getMovieVideos(movieId: Int = 1) = viewModelScope.launch {
        val videos = withContext(Dispatchers.IO) {
            detailsRepository
                .getMovieVideos(movieId)
        }
        movieVideos = videos?.results.orEmpty()
    }

}
