package com.example.techtrackr.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {
    private const val PREF_NAME = "techtrackr_prefs"
    private const val KEY_RECENTLY_VIEWED = "recently_viewed_products"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveRecentlyViewed(json: String) {
        prefs.edit().putString(KEY_RECENTLY_VIEWED, json).apply()
    }

    fun getRecentlyViewed(): String? {
        return prefs.getString(KEY_RECENTLY_VIEWED, null)
    }
}
