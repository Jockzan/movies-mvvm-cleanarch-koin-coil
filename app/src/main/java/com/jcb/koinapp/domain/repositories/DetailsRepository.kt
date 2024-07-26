package com.jcb.koinapp.domain.repositories

import com.jcb.koinapp.domain.models.MovieDetails
import com.jcb.koinapp.domain.models.MovieVideos

interface DetailsRepository {
    suspend fun getMovieDetails(id: Int): MovieDetails?

    suspend fun getMovieVideos(id: Int): MovieVideos?
}
