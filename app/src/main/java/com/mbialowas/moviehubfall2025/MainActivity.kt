package com.mbialowas.moviehubfall2025

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubfall2025.Navigation.BottomNav
import com.mbialowas.moviehubfall2025.api.MovieManager
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.db.AppDatabase
import com.mbialowas.moviehubfall2025.destinations.Destination
import com.mbialowas.moviehubfall2025.mvvm.MovieViewModel
import com.mbialowas.moviehubfall2025.screens.Counter
import com.mbialowas.moviehubfall2025.screens.MovieDetailScreen
import com.mbialowas.moviehubfall2025.screens.MovieScreen
import com.mbialowas.moviehubfall2025.screens.SearchScreen
import com.mbialowas.moviehubfall2025.screens.WatchScreen
import com.mbialowas.moviehubfall2025.ui.theme.MovieHubFall2025Theme
import com.mbialowas.moviehubfall2025.vm.AppViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubFall2025Theme {
                val navController = rememberNavController()
                val viewModel: MovieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
                val db = AppDatabase.getInstance(applicationContext)
                val movieManager = MovieManager(db)

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("MovieHub FALL 2025") }
                        )
                    },
                    bottomBar = {
                        BottomNav(navController = navController)
                    }
                ) { paddingValues ->
                    paddingValues.calculateBottomPadding()
                    Spacer(modifier = Modifier.padding(10.dp))
                    NavHost(
                        navController = navController,
                        startDestination = Destination.Movie.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Destination.Movie.route) {
                            MovieScreen(
                                modifier = Modifier,
                                movieManager = movieManager,
                                navController = navController,
                            )


                        }
                        composable(Destination.Search.route) {
                            SearchScreen()
                        }
                        composable(Destination.Watch.route) {
                            WatchScreen()
                        }
                        composable(Destination.MovieDetail.route){navBackStackEntry->
                            var movie by remember {
                                mutableStateOf<Movie?>(null)
                            }
                            val movie_id:String?  = navBackStackEntry.arguments?.getString("movieID")
                            //val m = Movie(title="The Matrix", overview = "A great story about a hacker.", posterPath = "https://media.themoviedb.org/t/p/w600_and_h900_bestv2/2LzFTywrUE3EY45fIhYQskiyshl.jpg")
                            //Log.i("MJB", m.id.toString() )
//                            MovieDetailScreen(
//                                modifier = Modifier,
//                                movie = m
//                            )
                            GlobalScope.launch {
                                if (movie_id != null ){
                                    movie = db.movieDao().getMovieById(movie_id.toInt())
                                }
                            }
                            movie?.let{
                                MovieDetailScreen(modifier = Modifier, movie=movie!!,db,navController,movieManager,viewModel)
                            }

                        }
                    }
                }
            }
        }
    }
}

