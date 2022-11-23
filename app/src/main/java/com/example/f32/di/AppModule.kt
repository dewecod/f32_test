package com.example.f32.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.f32.util.Constant.Companion.APP_PREFERENCES
import com.example.f32.util.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { provideSharedPreferences(androidApplication()) }
    single { providePreferenceManager(get()) }

    single { provideRequestOptions() }
    single { provideRequestManager(androidApplication(), get()) }
}

/****************************************************************************************************
 **  Shared preferences
 ****************************************************************************************************/
private fun provideSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
private fun providePreferenceManager(sharedPreferences: SharedPreferences): PreferenceManager = PreferenceManager(sharedPreferences)

/****************************************************************************************************
 **  Glide
 ****************************************************************************************************/
private fun provideRequestManager(application: Application, requestOptions: RequestOptions): RequestManager {
    return Glide.with(application).setDefaultRequestOptions(requestOptions)
}

private fun provideRequestOptions(): RequestOptions {
    return RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
}