package com.example.androidproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidproject.model.Localisation

@Database(entities = [Localisation::class], version = 2, exportSchema = false)
abstract class DatabaseLocalisation : RoomDatabase() {

    abstract val localisationDao: LocalisationDao


    companion object {
        @Volatile
        private var INSTANCE: DatabaseLocalisation? = null
        fun getInstance(context: Context): DatabaseLocalisation {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseLocalisation::class.java,
                        "database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}