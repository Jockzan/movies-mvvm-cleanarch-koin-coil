package com.jcb.koinapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jcb.koinapp.presentation.ui.movies.detail.MovieDetailsScreen
import com.jcb.koinapp.presentation.ui.movies.list.MoviesScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Movies.route,
) {
    NavHost(
        modifier = modifier,
        startDestination = startDestination,
        navController = navController,
    ) {
        composable(NavigationItem.Movies.route) {
            MoviesScreen(navController = navController)
        }
        composable(
            route = "${NavigationItem.MovieDetails.route}/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ) {
            val movieId = it.arguments?.getInt("movieId") ?: 1
            MovieDetailsScreen(movieId = movieId, navController = navController)
        }
    }
}