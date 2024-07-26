package com.jcb.koinapp.presentation.ui.navigation

enum class Screen {
    MOVIES,
    MOVIE_DETAILS
}

sealed class NavigationItem(val route: String) {
    object Movies : NavigationItem(Screen.MOVIES.name)
    object MovieDetails : NavigationItem(Screen.MOVIE_DETAILS.name)
}