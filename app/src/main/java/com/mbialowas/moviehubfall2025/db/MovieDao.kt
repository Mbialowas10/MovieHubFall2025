package com.mbialowas.moviehubfall2025.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mbialowas.moviehubfall2025.api.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)
    //INSERT INTO movies (SELECT * from api)
    // INSERT INTO INTO (movies) VALUES("title","overview", "posterPAHT")

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id:Int): Movie?




}