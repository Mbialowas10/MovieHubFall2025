package com.mbialowas.moviehubfall2025.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.mbialowas.moviehubfall2025.api.MovieManager
import com.mbialowas.moviehubfall2025.api.model.Movie


@Composable
fun MovieScreen(
    navController: NavController,
    modifier: Modifier,
    movieManager: MovieManager,
    //db:AppDatabase
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {







        val movies = movieManager.moviesResponse.value
        LazyColumn {
            items(movies) { movie ->
                MovieCard(movieItem = movie, navController = navController, movieManager)
                Log.i("HTTP:", "https://image.tmdb.org/t/p/w500${movie.posterPath}")
            }
        }
    }
}

@Composable
fun MovieCard(
    movieItem: Movie,
    navController: NavController,
    //db: AppDatabase,
    moviesManager: MovieManager
) {

    Column(
        modifier = Modifier
            .border(1.dp, Color.Red, shape = RoundedCornerShape(10.dp))

            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("movieDetail/${movieItem.id}")
            }
    ){
        Row(
            modifier = Modifier
                .background(color = Color.DarkGray)
                .fillMaxWidth()
                .padding(5.dp)
        ){
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data("https://image.tmdb.org/t/p/w500/${movieItem.posterPath}")
                    .build(),
                contentDescription = movieItem.overview,
                contentScale = ContentScale.FillWidth
            )
        }
    }

} // END MovieCard