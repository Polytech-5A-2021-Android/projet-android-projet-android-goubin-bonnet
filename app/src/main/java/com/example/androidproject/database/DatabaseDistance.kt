package com.example.androidproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidproject.model.Distance
import com.example.androidproject.model.Localisation


@Database(entities = [Distance::class], version = 1, exportSchema = false)
abstract class DatabaseDistance : RoomDatabase() {

    abstract val distanceDao : DistanceDao


    companion object {
        @Volatile
        private var INSTANCE: DatabaseDistance? = null
        fun getInstance(context: Context): DatabaseDistance {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseDistance::class.java,
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