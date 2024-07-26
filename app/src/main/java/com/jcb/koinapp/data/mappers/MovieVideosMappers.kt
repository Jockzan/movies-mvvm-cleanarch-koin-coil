package com.jcb.koinapp.data.mappers

import com.jcb.koinapp.data.models.MovieVideosModel
import com.jcb.koinapp.data.models.VideoModel
import com.jcb.koinapp.domain.models.MovieVideos
import com.jcb.koinapp.domain.models.Video

fun MovieVideosModel.toMovieVideos(): MovieVideos {
    return MovieVideos(
        id = this.id,
        results = this.results.map { it.toVideo() }
    )
}

fun VideoModel.toVideo(): Video {
    return Video(
        id = this.id,
        name = this.name,
        key = this.key,
        site = this.site,
        size = this.size,
        type = this.type,
        official = this.official,
        publishedAt = this.publishedAt,
        iso6391 = this.iso6391,
        iso31661 = this.iso31661
    )
}