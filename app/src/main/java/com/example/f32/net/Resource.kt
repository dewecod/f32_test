package com.example.f32.net

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}
