package com.example.f32.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "discount_price") val discountPrice: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "is_favorites") val isFavorites: Boolean,
    @Json(name = "picture") val picture: String,
    @Json(name = "price_without_discount") val priceWithoutDiscount: Int,
    @Json(name = "title") val title: String
)