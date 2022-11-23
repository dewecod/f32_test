package com.example.f32.net.response

import com.example.f32.model.Product
import com.example.f32.model.Slider
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IndexResponse(
    @Json(name = "home_store") val sliderList: List<Slider>,
    @Json(name = "best_seller") val productList: List<Product>,
)