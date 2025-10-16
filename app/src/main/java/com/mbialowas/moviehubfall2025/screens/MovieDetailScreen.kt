package com.mbialowas.moviehubfall2025.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mbialowas.moviehubfall2025.api.model.Movie

@Composable
fun MovieDetailScreen(modifier: Modifier, movie: Movie){
    Box(
        modifier
            .background(Color.Cyan)
            .fillMaxSize()
    ){
        Log.i("MovieDetailScreen","${movie.title}")
        Log.i("MovieDetailScreen","${movie.overview}")
        Log.i("MovieDetailScreen","${movie.posterPath}")
    }
}