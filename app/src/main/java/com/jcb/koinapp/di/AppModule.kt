package com.jcb.koinapp.di

import com.jcb.koinapp.BuildConfig
import com.jcb.koinapp.data.api.MoviesService
import com.jcb.koinapp.data.repositories.DetailsRepositoryImpl
import com.jcb.koinapp.data.repositories.MoviesRepositoryImpl
import com.jcb.koinapp.domain.repositories.DetailsRepository
import com.jcb.koinapp.domain.repositories.MoviesRepository
import com.jcb.koinapp.presentation.ui.movies.detail.MovieDetailsViewModel
import com.jcb.koinapp.presentation.ui.movies.list.MoviesViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule =
    module {
        single {
            Retrofit
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
        }
        single<MoviesRepository> {
            MoviesRepositoryImpl(get())
        }
        single<DetailsRepository> {
            DetailsRepositoryImpl(get())
        }
        viewModel {
            MoviesViewModel(get())
        }
        viewModel {
            MovieDetailsViewModel(get())
        }
    }
