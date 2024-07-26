package com.jcb.koinapp.data.repositories

import android.util.Log
import com.jcb.koinapp.BuildConfig
import com.jcb.koinapp.data.api.MoviesService
import com.jcb.koinapp.data.mappers.toMovieList
import com.jcb.koinapp.domain.models.MovieList
import com.jcb.koinapp.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesService: MoviesService,
) : MoviesRepository {

    private val headers by lazy {
        mapOf("Authorization" to "Bearer ${BuildConfig.API_TOKEN}")
    }

    override suspend fun getPopularMovies(page: Int): MovieList? {
        return try {
            val popularMovies = moviesService
                .getPopularMovies(headers, page)
            val toMovieList = popularMovies
                .toMovieList()
            toMovieList
        } catch (e: Exception) {
            Log.e("MoviesRepositoryImpl", "getPopularMovies: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun getTopRatedMovies(page: Int): MovieList? {
        return try {
            val topRatedMovies = moviesService
                .getTopRatedMovies(headers, page)
            val toMovieList = topRatedMovies
                .toMovieList()
            toMovieList
        } catch (e: Exception) {
            Log.e("MoviesRepositoryImpl", "getTopRatedMovies: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun getUpcomingMovies(page: Int): MovieList? {
        return try {
            moviesService
                .getUpcomingMovies(headers, page)
                .toMovieList()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): MovieList? {
        return try {
            moviesService
                .getNowPlayingMovies(headers, page)
                .toMovieList()
        } catch (e: Exception) {
            null
        }
    }
}
