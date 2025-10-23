package com.mbialowas.moviehubfall2025.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mbialowas.moviehubfall2025.api.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)
    // INSERT INTO MOVIES (SELECT * FROM API)
    // INSERT INTO Movies VALUES(a,b,c)

    // purpose here is to get movie detail infroomation for detail page
    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id:Int):Movie?

    @Update
    fun updateMovieState(movie:Movie)
    // UPDATE movies set isFavourite = new value boolean

    @Query("UPDATE movies SET title= :title, overview=:overview WHERE id = :id")
    fun updateMovie(id: Int, title:String, overview:String)

    @Delete
    suspend fun delete(movie:Movie)
    // DELETE from movies where movieId = {MovieId}

    @Query("SELECT * FROM Movies")
    fun getAllMovies() : List<Movie>
}