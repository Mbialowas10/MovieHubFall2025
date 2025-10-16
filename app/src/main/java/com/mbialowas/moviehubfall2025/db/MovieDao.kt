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
    // INSERT INTO MOVIES (SELECT * FROM API)
    // INSERT INTO Movies VALUES(a,b,c)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id:Int):Movie?

}