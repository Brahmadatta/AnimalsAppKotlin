package com.example.animalsappkotlin.util

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferenceHelper(context: Context) {

    private val PREF_API_KEY = "Api Key"

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key : String?)
    {
        prefs.edit().putString(PREF_API_KEY,key).apply()
    }

    fun getApiKey() = prefs.getString(PREF_API_KEY,null)
}