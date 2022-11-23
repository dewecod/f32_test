package com.example.f32.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.f32.di.dao.CartDao
import com.example.f32.model.Cart

@Database(
    entities = [
        Cart::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
