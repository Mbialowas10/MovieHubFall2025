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
    //INSERT INTO movies (SELECT * from api)
    // INSERT INTO INTO (movies) VALUES("title","overview", "posterPAHT")

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id:Int): Movie?

    @Delete
    suspend fun delete(movie: Movie)

    @Query("UPDATE movies SET title = :title, overview= :overview WHERE id = :movieID")
    fun updateMovie(movieID: Int, title: String, overview: String)

    @Update
    fun updateMovieState(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movie>





}