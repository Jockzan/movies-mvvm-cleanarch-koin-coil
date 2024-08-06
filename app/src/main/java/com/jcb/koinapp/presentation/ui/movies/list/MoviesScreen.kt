package com.jcb.koinapp.presentation.ui.movies.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jcb.koinapp.domain.models.Movie
import com.jcb.koinapp.presentation.ui.navigation.MovieDetails
import com.jcb.koinapp.presentation.utils.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel<MoviesViewModel>(),
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .imePadding()
            .background(Color.Black)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(
            Modifier.windowInsetsTopHeight(
                WindowInsets.statusBars
            )
        )
        MovieCategory(
            title = "Now Playing",
            movies = viewModel.nowPlayingMovies,
            navController = navController
        )
        MovieCategory(
            title = "Upcoming",
            movies = viewModel.upcomingMovies,
            navController = navController
        )
        MovieCategory(
            title = "Top Rated",
            movies = viewModel.topRatedMovies,
            navController = navController
        )
        MovieCategory(
            title = "Popular",
            movies = viewModel.popularMovies,
            navController = navController
        )
        Spacer(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.systemBars
            )
        )
    }
}

@Composable
fun MovieCategory(
    title: String,
    movies: List<Movie>,
    navController: NavController,
) {
    Column {
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        MoviesContent(
            movies = movies,
            onMovieSelected = {
                navController.navigate(MovieDetails(moveId = it))
            }
        )
    }
}

@Composable
private fun MoviesContent(
    movies: List<Movie>,
    onMovieSelected: (movieId: Int) -> Unit = {},
) {
    LazyRow(
        modifier =
        Modifier
            .height(150.dp)
            .fillMaxWidth(),
    ) {
        items(movies) { movie ->
            val model = "${Constants.IMAGE_URL}${movie.posterPath}"
            AsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .clickable { onMovieSelected(movie.id) },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(model)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Fit,
            )
        }
    }
}
