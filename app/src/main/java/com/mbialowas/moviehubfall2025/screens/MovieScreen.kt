package com.mbialowas.moviehubfall2025.screens

import android.R.attr.fontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MovieScreen(modifier: Modifier =  Modifier){
    Box(
        modifier
            .fillMaxSize()
            .background(Color.Blue)
    ){
        Text(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align (Alignment.Center),
            text="Movie Screen"
        )
    }
}