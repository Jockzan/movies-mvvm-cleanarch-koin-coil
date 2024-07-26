package com.jcb.koinapp.data.api/*
package com.jcb.koinapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieServiceApi: MoviesService {

    private val baseUrl: String = "https://api.themoviedb.org/3/"

    private val client by lazy {
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .retryOnConnectionFailure(true)
            .build()
    }

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    private val moviesService by lazy {
        retrofit.create(MoviesService::class.java) as MoviesService
    }

    override suspend fun getPopularMovies(headers: Map<String, String>, page: Int): MovieListModel {
        return moviesService.getPopularMovies(headers, page)
    }

    override suspend fun getTopRatedMovies(
        headers: Map<String, String>,
        page: Int
    ): MovieListModel {
        return moviesService.getTopRatedMovies(headers, page)
    }

    override suspend fun getUpcomingMovies(
        headers: Map<String, String>,
        page: Int
    ): MovieListModel {
        return moviesService.getUpcomingMovies(headers, page)
    }

    override suspend fun getNowPlayingMovies(
        headers: Map<String, String>,
        page: Int
    ): MovieListModel {
        return moviesService.getNowPlayingMovies(headers, page)
    }

    override suspend fun getMovieVideos(headers: Map<String, String>, id: Int): MovieVideosModel {
        return moviesService.getMovieVideos(headers, id)
    }

}*/
