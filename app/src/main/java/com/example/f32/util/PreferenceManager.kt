package com.example.f32.util

import android.content.SharedPreferences
import com.example.f32.util.Constant.Companion.PREF_USER_ID
import com.example.f32.util.Constant.Companion.PREF_USER_NAME
import com.example.f32.util.Constant.Companion.PREF_USER_PHONE

class PreferenceManager constructor(private val sharedPreferences: SharedPreferences) {

    var prefUserId: Int
        get() = sharedPreferences.getInt(PREF_USER_ID, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREF_USER_ID, value).apply()
        }

    var prefUsername: String
        get() = sharedPreferences.getString(PREF_USER_NAME, "").toString()
        set(value) {
            sharedPreferences.edit().putString(PREF_USER_NAME, value).apply()
        }

    var prefUserPhone: String
        get() = sharedPreferences.getString(PREF_USER_PHONE, "").toString()
        set(value) {
            sharedPreferences.edit().putString(PREF_USER_PHONE, value).apply()
        }


    fun remove(key: String?) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().remove(PREF_USER_ID).apply()
        sharedPreferences.edit().remove(PREF_USER_NAME).apply()
        sharedPreferences.edit().remove(PREF_USER_PHONE).apply()
    }
}
