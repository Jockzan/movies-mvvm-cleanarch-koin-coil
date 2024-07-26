package com.jcb.koinapp.data.repositories

import com.jcb.koinapp.BuildConfig
import com.jcb.koinapp.data.api.MoviesService
import com.jcb.koinapp.data.mappers.toMovieDetails
import com.jcb.koinapp.data.mappers.toMovieVideos
import com.jcb.koinapp.domain.models.MovieDetails
import com.jcb.koinapp.domain.models.MovieVideos
import com.jcb.koinapp.domain.repositories.DetailsRepository

class DetailsRepositoryImpl(
    private val moviesService: MoviesService,
) : DetailsRepository {

    private val headers by lazy {
        mapOf("Authorization" to "Bearer ${BuildConfig.API_TOKEN}")
    }

    override suspend fun getMovieDetails(id: Int): MovieDetails? {
        return try {
            moviesService
                .getMovieDetails(headers, id)
                .toMovieDetails()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMovieVideos(id: Int): MovieVideos? {
        return try {
            moviesService
                .getMovieVideos(headers, id)
                .toMovieVideos()
        } catch (e: Exception) {
            null
        }
    }
}
