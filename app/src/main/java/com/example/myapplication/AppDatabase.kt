package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if(instance == null) {
                synchronized(AppDatabase::class.java) {
                    instance = Room.databaseBuilder(context,
                        AppDatabase::class.java, "appDatabase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}