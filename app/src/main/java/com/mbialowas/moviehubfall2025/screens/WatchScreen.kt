package com.mbialowas.moviehubfall2025.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.mbialowas.moviehubfall2025.api.MovieManager
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.db.AppDatabase

@Composable
fun WatchScreen(
    modifier: Modifier = Modifier,
    db: AppDatabase,
    movieManager: MovieManager,
    navController: NavController

){
    Box(
        modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {

        var data by remember { mutableStateOf<List<Movie>>(emptyList()) }


        // reference to our movie collection
        val collectionReference = FirebaseFirestore.getInstance().collection("movies")

        collectionReference
            .get()
            .addOnSuccessListener { documents ->
                val dataList = documents.map{documentSnapshot ->

                    val dataMap = documentSnapshot.data // data from the firebase collection
                    // converting the firestore database JSON object to a Movie object
                    Movie(
                        id = (dataMap["movie_id"] as String).toInt(),
                        overview = dataMap["movie_overview"] as? String,
                        voteAverage = (dataMap["movie_vote_average"] as? String)?.toDouble(),
                        voteCount = (dataMap["movie_vote_count"] as? String)?.toInt(),
                        popularity = (dataMap["movie_popularity"] as? String)?.toDouble(),
                        posterPath = dataMap["movie_poster_path"] as? String,
                        releaseDate = dataMap["movie_release_date"] as? String,
                        title = dataMap["movie_title"] as? String,
                        isFavourite = (dataMap["isFavourite"] as? String).toBoolean()
                    )
                }
                data = dataList
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.e("Firestore Response", "Error getting documents: $exception")
            }
        Column{
            Text(text="Watch later Screen")
            LazyColumn{
                items(data){ movie->
                    MovieCard(
                        movieItem = movie,
                        navController = navController,
                        database = db,
                        movieManager = movieManager,
                        modifier = modifier
                    )
                }
            }

        }
    }
}