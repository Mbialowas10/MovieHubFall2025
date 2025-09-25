package com.mbialowas.moviehubfall2025.Navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mbialowas.moviehubfall2025.R

@Composable
fun BottomNav(navController: NavController){
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val ic_movie = painterResource(id= R.drawable.ic_movie)
        val ic_watch = painterResource(id=R.drawable.ic_watch)
        val ic_search = painterResource(id=R.drawable.ic_watch)
    }
}