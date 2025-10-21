package com.mbialowas.moviehubfall2025.mvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel(){

    // movies list
    val movies = mutableStateOf<List<Movie>>(emptyList())

    // variable to keep track of movie icon state
    private val _movieIconState = MutableStateFlow<Map<Int,Boolean>>(emptyMap())

    val movieIconState = _movieIconState.asStateFlow()

    /**
     * Purpose - a function to update the movie Favrourite statee ie.
     * true or false
     * @ param movieID: Int - represent the movieID
     * @ param database: AppDatabase - represent the database
     * @ return unit
     * @ exception - NA
     */
    fun updateMovieIconState(movieId: Int, database: AppDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            val movie = database.movieDao().getMovieById(movieId)

            if (movie != null){
                movie.isFavourite = !movie.isFavourite

                // update movie in the databa with new state
                launch(Dispatchers.IO){
                    database.movieDao().updateMovieState(movie)

                    _movieIconState.value.toMutableMap().apply{
                        this[movieId] = movie.isFavourite
                    }
                }
            }else{
                Log.e("MovieViewModel", "Movie with ID $movieId  not found in db.")
            }
        }
    }

}