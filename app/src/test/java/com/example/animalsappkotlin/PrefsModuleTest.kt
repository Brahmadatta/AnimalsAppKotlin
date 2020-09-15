package com.example.animalsappkotlin

import android.app.Application
import com.example.animalsappkotlin.di.PrefsModule
import com.example.animalsappkotlin.util.SharedPreferenceHelper

class PrefsModuleTest(val mockPrefs : SharedPreferenceHelper) : PrefsModule() {
    override fun provideSharedPreferences(app: Application): SharedPreferenceHelper {
        return mockPrefs
    }
}