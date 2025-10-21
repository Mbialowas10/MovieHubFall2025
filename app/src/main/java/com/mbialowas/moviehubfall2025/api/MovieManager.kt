package com.mbialowas.moviehubfall2025.api

import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.os.BuildCompat
import com.mbialowas.moviehubfall2025.BuildConfig
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.api.model.MovieData
import com.mbialowas.moviehubfall2025.db.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MovieManager(
    database: AppDatabase
){
    private var _moviesResponse= mutableStateOf<List<Movie>>(emptyList())
    //val api_key="23ddbf51b97364a6da401d3a7ce6f4ed" # don't do this!
    val api_key = BuildConfig.TMDB_API_KEY
    private val database = database

    val moviesResponse: MutableState<List<Movie>>
        @Composable get() = remember {
            _moviesResponse
        }
    init{
        getMovies(database)
    }
    private fun getMovies(
        database: AppDatabase
    ){
        val service = Api.retrofitService.getTrendingMovies(api_key)


        service.enqueue(object : retrofit2.Callback<MovieData> {
            override fun onResponse(
                call: Call<MovieData?>,
                response: Response<MovieData?>
            ) {
                if (response.isSuccessful){
                    Log.i("Data","Data is locked and loaded. ")

                    _moviesResponse.value = response.body()?.results ?: emptyList()
                    Log.i("DataStream", _moviesResponse.value.toString())
                    GlobalScope.launch {
                        saveDataToDatabase(database=database, _moviesResponse.value)
                    }
                }
            }
            private suspend fun saveDataToDatabase(
                database: AppDatabase,
                movies: List<Movie>
            ){
                database.movieDao().insertAllMovies(movies)
            }

            override fun onFailure(
                call: Call<MovieData?>,
                t: Throwable
            ) {
                Log.d("error","${t.message}")
            }

        })
    }
    suspend fun  refreshMovies(){
        var movies = database.movieDao().getAllMovies()
        _moviesResponse.value = movies
    }
}



