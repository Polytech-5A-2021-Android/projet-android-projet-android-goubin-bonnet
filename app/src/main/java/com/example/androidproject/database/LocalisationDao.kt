package com.example.androidproject.database

import androidx.room.*
import com.example.androidproject.model.Localisation


@Dao
interface LocalisationDao {

    @Insert
    fun insert(localisation: Localisation): Long
    @Delete
    fun delete(localisation: Localisation)
    @Update
    fun update(localisation: Localisation)

    @Query("SELECT * from localisation WHERE id = :key")
    fun get(key: Long): Localisation

    @Query("SELECT * FROM localisation ORDER BY id DESC LIMIT 1")
    fun getLastLocalisation(): Localisation?

    @Query("SELECT * FROM localisation ORDER BY id DESC LIMIT 3")
    fun getLast3Localisation(): List<Localisation>

    @Query("SELECT * FROM localisation")
    fun getLocalisations(): List<Localisation>?


}