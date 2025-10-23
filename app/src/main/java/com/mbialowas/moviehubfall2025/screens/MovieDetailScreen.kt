package com.mbialowas.moviehubfall2025.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.mbialowas.moviehubfall2025.api.MovieManager
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.db.AppDatabase
import com.mbialowas.moviehubfall2025.destinations.Destination
import com.mbialowas.moviehubfall2025.mvvm.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MovieDetailScreen(
    modifier: Modifier,
    movie: Movie,
    movieManager: MovieManager,
    db: AppDatabase,
    viewModel: MovieViewModel,
    navController: NavController

){
    Box(
        modifier
            .background(Color.Black)
            .fillMaxSize()
    ){
        Log.i("MovieDetailScreen","${movie.title}")
        Log.i("MovieDetailScreen","${movie.overview}")
        Log.i("MovieDetailScreen","${movie.posterPath}")


        // state level variable to track movie favourite state
        val iconState by viewModel.movieIconState.collectAsState()
        var isIconChanged = iconState[movie.id] == true
        var showEditDialog by remember {mutableStateOf(false)}
        var showDeleteDialog by remember { mutableStateOf(false) }


        Column{
            Text(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(),
                text = "Movie Detail Screen",
                style = MaterialTheme.typography.headlineMedium,
                color=Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            // Image Box with overlaid buttons similar to
            // a div in web dev
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ){
                AsyncImage(
                    modifier = Modifier
                        .matchParentSize(),
                    model= ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                        .build(),
                    contentDescription = movie.overview,
                    contentScale = ContentScale.FillBounds
                )
                IconButton(
                    onClick = {
                        isIconChanged = !isIconChanged
                        // update movie state inside the view model
                        viewModel.updateMovieIconState(movie.id,db)
                              },

                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha=0.6f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isIconChanged) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Watch Later",
                        tint=Color.White
                    )
                }
                // Edit/Delete Button
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Button(
                        onClick = {
                            showEditDialog = true
                        }
                    ) {
                        Text("Edit")
                    }
                    Button(
                        onClick = {
                            showDeleteDialog= true
                        }
                    ) {
                        Text("Delete")
                    }
                    //show dialogs
                    if (showDeleteDialog){
                        DeleteMovieDialog(
                            movie = movie,
                            onDismiss = { showDeleteDialog = false},
                            onConfirmDelete = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    // call our movie doa
                                    db.movieDao().delete(movie)
                                    Log.i("MJB", "Past delete BP!")
                                    movieManager.refreshMovies()
                                }
                                showDeleteDialog = false
                                navController.navigate(Destination.Movie.route)


                            }
                        )
                    } // end DeleteDialog
                    if (showEditDialog){
                        EditMovieDialog(
                            movie = movie,
                            onDismiss = { showEditDialog = false },
                            onConfirmEdit = {newTitle,newDescription ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    movie.id?.let{movie->
                                        // update in the database
                                        db.movieDao().updateMovie(movie,newTitle,newDescription)
                                        Log.i("MJB", "Past edit BP!")
                                        movieManager.refreshMovies()
                                    }
                                }
                                showEditDialog = false
                                navController.navigate(Destination.Movie.route)

                            }
                        )
                    }
                } // end of Edit/Delete row

            } // end of the image box
            Column(
                Modifier.padding(20.dp)
            ){
                Spacer(
                    modifier = Modifier.padding(5.dp))

                movie.releaseDate?.let{
                    Text(
                        text = "Release Date: $it",
                        modifier = Modifier.padding(end=5.dp),
                        maxLines = 1,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    movie.overview?.let {
                        Text(
                            text= it,
                            modifier = Modifier.padding(end=8.dp),
                            maxLines = 3,
                            fontSize = 16.sp,
                            overflow= TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color=Color.White
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row {
                        movie.voteAverage?.let {
                            Text(
                                text = "Avg Vote: $it",
                                modifier = Modifier.padding(end = 8.dp),
                                maxLines = 1,
                                fontSize = 20.sp,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                        }
                        movie.voteCount?.let {
                            Text(
                                text = "# of votes: $it",
                                modifier = Modifier.padding(end = 8.dp),
                                maxLines = 1,
                                fontSize = 20.sp,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}