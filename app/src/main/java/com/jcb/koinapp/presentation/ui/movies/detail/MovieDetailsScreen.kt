package com.jcb.koinapp.presentation.ui.movies.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jcb.koinapp.domain.models.ProductionCompany
import com.jcb.koinapp.domain.models.Video
import com.jcb.koinapp.presentation.utils.Constants
import com.jcb.koinapp.presentation.utils.toMedium
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = getViewModel<MovieDetailsViewModel>(),
    movieId: Int = 1,
    navController: NavController
) {
    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId)
        viewModel.getMovieVideos(movieId)
    }

    val scrollState = rememberScrollState()

    val movieDetails = viewModel.movieDetails
    if (movieDetails != null) {
        Column(
            modifier = Modifier
                .imePadding()
                .background(Color.Black)
                .fillMaxSize()
        ) {
            val model = "${Constants.IMAGE_URL}${movieDetails.backdropPath}"
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(model)
                    .crossfade(true)
                    .build(),
                contentDescription = movieDetails.title,
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = movieDetails.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                if (movieDetails.tagline != null) {
                    Text(
                        text = movieDetails.tagline,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        fontStyle = FontStyle.Italic
                    )
                }
                Text(
                    text = movieDetails.overview,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                if (viewModel.movieVideos.isNotEmpty()) {
                    Text(
                        text = "Trailers:",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    MoviesPreviewContent(
                        videos = viewModel.movieVideos.filter {
                            it.site.contains("YouTube", true)
                        },
                        context = navController.context
                    )
                }
                if (movieDetails.genres.isNotEmpty()) {
                    Text(
                        text = "Genre:",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = movieDetails.genres.joinToString { it.name },
                        color = Color.White,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                Text(
                    text = "Release Date:",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = movieDetails.releaseDate.toMedium(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
                if (movieDetails.productionCompanies.isNotEmpty()) {
                    MoviesProductionCompaniesContent(
                        movieDetails.productionCompanies
                    )
                }
                val movPst = "${Constants.IMAGE_URL}${movieDetails.posterPath}"
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movPst)
                        .crossfade(true)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .build(),
                    contentDescription = movieDetails.title,
                    contentScale = ContentScale.Fit,
                )
                Spacer(
                    Modifier.windowInsetsBottomHeight(
                        WindowInsets.systemBars
                    )
                )
            }
        }
    }
}

@Composable
private fun MoviesPreviewContent(
    videos: List<Video>,
    context: Context,
) {
    LazyRow(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(videos) { video ->
            val model =
                "${Constants.YOUTUBE_IMAGE_URL_PRE}${video.key}${Constants.YOUTUBE_IMAGE_URL_POST}"
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(100.dp)
                        .height(80.dp)
                        .clickable { openYouTube(videoId = video.key, context = context) },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(model)
                        .crossfade(true)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .build(),
                    contentDescription = video.site,
                    contentScale = ContentScale.Fit,
                )
                Text(
                    modifier = Modifier
                        .width(100.dp),
                    text = video.name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}

@Composable
private fun MoviesProductionCompaniesContent(
    companies: List<ProductionCompany>
) {
    LazyRow(
        modifier =
        Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(companies) { company ->
            val model = "${Constants.IMAGE_URL}${company.logoPath}"
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(model)
                        .crossfade(true)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .build(),
                    contentDescription = company.name,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
private fun openYouTube(videoId: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
    intent.putExtra("VIDEO_ID", videoId)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // Fallback to browser if YouTube app is not available
        val webIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("${Constants.YOUTUBE_URL}$videoId"))
        context.startActivity(webIntent)
    }
}