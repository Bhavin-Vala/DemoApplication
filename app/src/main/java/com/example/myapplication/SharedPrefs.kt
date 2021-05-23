package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences


class SharedPrefs(context: Context) {

    val  PREF_NAME = "userPref"
    val KEY_TEXT = "text_size"

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME ,Context.MODE_PRIVATE)

    var textSize: Int
        get() = preferences.getInt(KEY_TEXT, 16)
        set(value) = preferences.edit().putInt(KEY_TEXT, value).apply()
}