package com.example.f32.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Basket(
    @Json(name = "id") val id: Int,
    @Json(name = "images") val images: String,
    @Json(name = "price") val price: Int,
    @Json(name = "title") val title: String
)