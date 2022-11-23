package com.example.f32.di

import com.example.f32.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel(get()) }
}