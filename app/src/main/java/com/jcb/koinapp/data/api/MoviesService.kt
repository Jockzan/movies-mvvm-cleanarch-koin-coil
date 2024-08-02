package com.jcb.koinapp.data.api

import com.jcb.koinapp.BuildConfig
import com.jcb.koinapp.data.models.MovieDetailsModel
import com.jcb.koinapp.data.models.MovieListModel
import com.jcb.koinapp.data.models.MovieVideosModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @HeaderMap headers: Map<String, String>,
        @Query("page") page: Int,
    ): MovieListModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @HeaderMap headers: Map<String, String>,
        @Query("page") page: Int,
    ): MovieListModel

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @HeaderMap headers: Map<String, String>,
        @Query("page") page: Int,
    ): MovieListModel

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @HeaderMap headers: Map<String, String>,
        @Query("page") page: Int,
    ): MovieListModel

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int,
    ): MovieVideosModel

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int,
    ): MovieDetailsModel
}

fun createMoviesService() = Retrofit
    .Builder()
    .baseUrl(BuildConfig.API_URL)
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )
    )
    .client(
        OkHttpClient
            .Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            ).retryOnConnectionFailure(true)
            .build(),
    ).build()
    .create(MoviesService::class.java) as MoviesService
