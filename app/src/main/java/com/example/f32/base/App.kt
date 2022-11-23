package com.example.f32.base

import android.app.Application
import com.example.f32.BuildConfig
import com.example.f32.di.appModule
import com.example.f32.di.databaseModule
import com.example.f32.di.networkModule
import com.example.f32.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(listOf(appModule, networkModule, databaseModule, viewModelModule))
        }
    }
}