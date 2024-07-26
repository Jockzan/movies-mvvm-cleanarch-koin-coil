package com.jcb.koinapp.presentation.ui.movies.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcb.koinapp.domain.models.Movie
import com.jcb.koinapp.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    var nowPlayingMovies by mutableStateOf(listOf<Movie>())
    var upcomingMovies by mutableStateOf(listOf<Movie>())
    var topRatedMovies by mutableStateOf(listOf<Movie>())
    var popularMovies by mutableStateOf(listOf<Movie>())

    init {
        getNowPlayingMovies()
        getUpcomingMovies()
        getTopRatedMovies()
        getPopularMovies()
    }

    private fun getNowPlayingMovies(page: Int = 1) = viewModelScope.launch {
        val movies = withContext(Dispatchers.IO) {
            moviesRepository
                .getNowPlayingMovies(page)
                ?.results
                ?: emptyList()
        }
        nowPlayingMovies = movies
    }

    private fun getUpcomingMovies(page: Int = 1) = viewModelScope.launch {
        val movies = withContext(Dispatchers.IO) {
            moviesRepository
                .getUpcomingMovies(page)
                ?.results
                ?: emptyList()
        }
        upcomingMovies = movies
    }

    private fun getTopRatedMovies(page: Int = 1) = viewModelScope.launch {
        val movies = withContext(Dispatchers.IO) {
            moviesRepository
                .getTopRatedMovies(page)
                ?.results
                ?: emptyList()
        }
        topRatedMovies = movies
    }

    private fun getPopularMovies(page: Int = 1) = viewModelScope.launch {
        val movies = withContext(Dispatchers.IO) {
            moviesRepository
                .getPopularMovies(page)
                ?.results
                ?: emptyList()
        }
        popularMovies = movies
    }

    fun onMovieSelected(movieId: Int) {

    }

}
