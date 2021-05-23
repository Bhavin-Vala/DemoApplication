package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllData(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user")
    fun getData(): UserEntity
}