package com.jcb.koinapp.domain.repositories

import com.jcb.koinapp.domain.models.MovieList

interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): MovieList?

    suspend fun getTopRatedMovies(page: Int): MovieList?

    suspend fun getUpcomingMovies(page: Int): MovieList?

    suspend fun getNowPlayingMovies(page: Int): MovieList?
}
