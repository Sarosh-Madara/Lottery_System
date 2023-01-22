package com.lotterysystem.app.datastore.helper

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesHelper {

    private val TOTAL_SCORE_KEY = "TotalScore"


    public fun setUserScore(score: Int,context: Context) {
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .edit().putInt(TOTAL_SCORE_KEY, score).apply()
    }


    public fun getUserScore(context: Context): Int {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .getInt(TOTAL_SCORE_KEY, 0)
    }
}