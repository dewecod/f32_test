package com.example.f32.net.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @Json(name = "CPU") val cPU: String,
    @Json(name = "camera") val camera: String,
    @Json(name = "capacity") val capacity: List<String>,
    @Json(name = "color") val color: List<String>,
    @Json(name = "id") val id: String,
    @Json(name = "images") val images: List<String>,
    @Json(name = "isFavorites") val isFavorites: Boolean,
    @Json(name = "price") val price: Int,
    @Json(name = "rating") val rating: Double,
    @Json(name = "sd") val sd: String,
    @Json(name = "ssd") val ssd: String,
    @Json(name = "title") val title: String
)