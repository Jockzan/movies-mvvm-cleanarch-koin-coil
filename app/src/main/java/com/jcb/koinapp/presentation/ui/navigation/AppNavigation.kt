package com.jcb.koinapp.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Movies

@Serializable
data class MovieDetails(val moveId: Int)