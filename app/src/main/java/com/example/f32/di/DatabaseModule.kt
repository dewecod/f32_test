package com.example.f32.di

import android.app.Application
import androidx.room.Room
import com.example.f32.db.AppDatabase
import com.example.f32.di.dao.CartDao
import com.example.f32.util.Constant.Companion.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideAppDb(androidApplication()) }

    single { provideCartDao(get()) }
}

/****************************************************************************************************
 ** AppDatabase
 ****************************************************************************************************/
fun provideAppDb(app: Application): AppDatabase {
    return Room
        .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration() // get correct db version if schema changed
        .build()
}

/****************************************************************************************************
 ** Dao
 ****************************************************************************************************/
fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
