package com.example.f32.util

import java.text.SimpleDateFormat
import java.util.*

class Constant {
    companion object {
        const val BASE_URL = "https://run.mocky.io/"
        const val INDEX_API = "v3/654bd15e-b121-49ba-a588-960956b15175"
        const val PRODUCT_API = "v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5"
        const val CART_API = "v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149"
        const val NETWORK_TIMEOUT: Long = 30
        const val MAX_MEMORY_CACHE: Long = 20 * 1024 * 1024

        const val DATABASE_NAME = "F32_DB"

        // Bottom tab index
        const val INDEX_HOME = 0
        const val INDEX_CART = 1
        const val INDEX_FAVORITE = 2
        const val INDEX_PROFILE = 3

        // Shared preferences
        const val APP_PREFERENCES: String = "com.clearylabs.garagum.APP_PREFERENCES"
        const val PREF_USER_ID = "com.clearylabs.garagum.userId"
        const val PREF_USER_NAME = "com.clearylabs.garagum.userName"
        const val PREF_USER_PHONE = "com.clearylabs.garagum.userPhone"

        val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val apiDayFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        val apiMonthFormat = SimpleDateFormat("MM", Locale.ENGLISH)
        val apiYearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val apiTimeFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val apiDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    }
}
