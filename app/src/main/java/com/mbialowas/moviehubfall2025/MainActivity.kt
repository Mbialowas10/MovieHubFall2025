package com.mbialowas.moviehubfall2025

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbialowas.moviehubfall2025.Navigation.BottomNav
import com.mbialowas.moviehubfall2025.api.MovieManager
import com.mbialowas.moviehubfall2025.destinations.Destination
import com.mbialowas.moviehubfall2025.screens.Counter
import com.mbialowas.moviehubfall2025.screens.MovieScreen
import com.mbialowas.moviehubfall2025.screens.SearchScreen
import com.mbialowas.moviehubfall2025.screens.WatchScreen
import com.mbialowas.moviehubfall2025.ui.theme.MovieHubFall2025Theme
import com.mbialowas.moviehubfall2025.vm.AppViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieHubFall2025Theme {
                val navController = rememberNavController()
                val viewModel: AppViewModel = ViewModelProvider(this)[AppViewModel::class.java]
                val movieManager = MovieManager()
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
                                movieManager = movieManager
                            )


                        }
                        composable(Destination.Search.route) {
                            SearchScreen()
                        }
                        composable(Destination.Watch.route) {
                            WatchScreen()
                        }
                    }
                }
            }
        }
    }
}

