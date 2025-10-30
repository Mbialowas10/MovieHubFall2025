package com.mbialowas.moviehubfall2025.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbialowas.moviehubfall2025.api.model.Movie

@Database(entities = [Movie::class], version=3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract  fun movieDao(): MovieDao

    companion object{
        // use the companion object
        // to implement the Singleton Pattern. Oh My!
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance  = Room.databaseBuilder(
                    context = context.applicationContext,
                    AppDatabase::class.java,
                    "MovieHub FALL 2025"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}