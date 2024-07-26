package com.jcb.koinapp.data.mappers

import com.jcb.koinapp.data.models.DatesModel
import com.jcb.koinapp.data.models.MovieListModel
import com.jcb.koinapp.data.models.MovieModel
import com.jcb.koinapp.domain.models.Dates
import com.jcb.koinapp.domain.models.Movie
import com.jcb.koinapp.domain.models.MovieList

fun MovieListModel.toMovieList(): MovieList {
    return MovieList(
        dates = this.dates?.toDates(),
        page = this.page,
        results = this.results.map { it.toMovie() },
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}

fun DatesModel.toDates(): Dates {
    return Dates(
        maximum = this.maximum,
        minimum = this.minimum
    )
}

fun MovieModel.toMovie(): Movie {
    return Movie(
        id = this.id,
        overview = this.overview,
        popularity = this.popularity,
        title = this.title,
        video = this.video,
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}