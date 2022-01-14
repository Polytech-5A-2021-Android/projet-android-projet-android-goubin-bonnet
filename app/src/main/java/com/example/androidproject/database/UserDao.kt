package com.example.androidproject.database

import androidx.room.*
import com.example.androidproject.model.User

@Dao
interface UserDao
{
    @Insert
    fun insert(user: User): Long
    @Delete
    fun delete(user: User)
    @Update
    fun update(user: User)
    @Query("SELECT * from user WHERE id = :key")
    fun get(key: Long): User?
    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    fun getLastUser(): User ?
    @Query("SELECT * FROM user")
    fun getUsers(): List<User>?
    @Query("SELECT * FROM user WHERE password = :password AND firstname = :username ")
    fun getByLogin(username : String?, password : String?): User?
}
