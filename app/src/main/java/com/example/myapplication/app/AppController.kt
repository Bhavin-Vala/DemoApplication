package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.AppDatabase
import com.example.myapplication.SharedPrefs

class AppController : Application() {

    companion object {
        var appDatabase: AppDatabase? = null
        var sharedPrefs: SharedPrefs? = null

        fun getAppDatabaseInstance(): AppDatabase? {
            return appDatabase
        }
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getInstance(applicationContext)
        sharedPrefs = SharedPrefs(applicationContext)
    }
}