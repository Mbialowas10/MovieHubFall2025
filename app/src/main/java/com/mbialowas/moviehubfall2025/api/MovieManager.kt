package com.mbialowas.moviehubfall2025.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mbialowas.moviehubfall2025.api.model.Movie
import com.mbialowas.moviehubfall2025.api.model.MovieData
import retrofit2.Call
import retrofit2.Response

class MovieManager{
    private var _moviesResponse= mutableStateOf<List<Movie>>(emptyList())
    val api_key="23ddbf51b97364a6da401d3a7ce6f4ed"

    val moviesResponse: MutableState<List<Movie>>
        @Composable get() = remember {
            _moviesResponse
        }
    init{
        getMovies()
    }
    private fun getMovies(){
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
                }
            }

            override fun onFailure(
                call: Call<MovieData?>,
                t: Throwable
            ) {
                Log.d("error","${t.message}")
            }

        })
    }
}



