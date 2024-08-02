package com.jcb.koinapp.di

import com.jcb.koinapp.data.api.createMoviesService
import com.jcb.koinapp.data.repositories.DetailsRepositoryImpl
import com.jcb.koinapp.data.repositories.MoviesRepositoryImpl
import com.jcb.koinapp.domain.repositories.DetailsRepository
import com.jcb.koinapp.domain.repositories.MoviesRepository
import com.jcb.koinapp.presentation.ui.movies.detail.MovieDetailsViewModel
import com.jcb.koinapp.presentation.ui.movies.list.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        single {
            createMoviesService()
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
