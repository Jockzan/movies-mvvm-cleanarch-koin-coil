package com.jcb.koinapp.presentation.ui.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.jcb.koinapp.presentation.theme.KoinAppTheme
import com.jcb.koinapp.presentation.ui.navigation.AppNavHost

class MoviesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinAppTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}
