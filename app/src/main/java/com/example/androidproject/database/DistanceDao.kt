package com.example.androidproject.database

import androidx.room.*
import com.example.androidproject.model.Distance


@Dao
interface DistanceDao
{

    @Insert
    fun insert(distance: Distance): Long
    @Delete
    fun delete(distance: Distance)
    @Update
    fun update(distance: Distance)
    @Query("SELECT * FROM distance ORDER BY id DESC LIMIT 1")
    fun getLastDistance(): Distance?

}