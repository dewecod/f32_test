package com.example.f32.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Slider(
    @Json(name = "id") val id: Int,
    @Json(name = "is_buy") val isBuy: Boolean,
    @Json(name = "is_new") val isNew: Boolean = false,
    @Json(name = "picture") val picture: String,
    @Json(name = "subtitle") val subtitle: String,
    @Json(name = "title") val title: String
)