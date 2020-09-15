package com.example.animalsappkotlin.di

import android.app.Activity
import android.app.Application
import com.example.animalsappkotlin.util.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    fun provideSharedPreferences(app: Application) : SharedPreferenceHelper {
        return SharedPreferenceHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: Activity) : SharedPreferenceHelper{
        return SharedPreferenceHelper(activity)
    }

}

const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY = "Activity Context"

@Qualifier
annotation class TypeOfContext(val type : String)