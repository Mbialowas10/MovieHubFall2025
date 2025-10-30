package com.mbialowas.moviehubfall2025.mvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbialowas.moviehubfall2025.BuildConfig
import com.mbialowas.moviehubfall2025.api.Api
import com.mbialowas.moviehubfall2025.db.AppDatabase
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.api.model.MovieData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieViewModel : ViewModel(){
    // api key
    var api_key: String = BuildConfig.TMDB_API_KEY

    //search term
    val searchTerm = mutableStateOf("")

    // movies list
    val movies = mutableStateOf<List<Movie>>(emptyList())

    // variable to keep track of movie icon state
    private val _movieIconState = MutableStateFlow<Map<Int,Boolean>>(emptyMap())

    val movieIconState = _movieIconState.asStateFlow()

    fun searchMovie(movieName:String, database: AppDatabase){

        // api call
        val service = Api.retrofitService.searchMovieByName(api_key, movieName)

        service.enqueue(object : Callback<MovieData> {
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                Log.i("SearchDta","testing api call")
                movies.value = response.body()?.results ?: emptyList()
                Log.i("SearchData", movies.value.toString())
                GlobalScope.launch{
                    database.movieDao().insertAllMovies(movies.value)
                }

            }

            override fun onFailure(
                call: Call<MovieData>,
                t: Throwable
            ) {
                Log.d("Error", "${t.message}")
            }

        })
    }

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