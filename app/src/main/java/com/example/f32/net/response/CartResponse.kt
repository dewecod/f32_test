package com.example.f32.net.response


import com.example.f32.model.Basket
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartResponse(
    @Json(name = "basket") val basketList: List<Basket>,
    @Json(name = "delivery") val delivery: String,
    @Json(name = "id") val id: String,
    @Json(name = "total") val total: Int
)