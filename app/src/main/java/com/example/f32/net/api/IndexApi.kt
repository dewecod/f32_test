package com.example.f32.net.api

import com.example.f32.net.response.CartResponse
import com.example.f32.net.response.IndexResponse
import com.example.f32.net.response.ProductResponse
import com.example.f32.util.Constant
import retrofit2.Response
import retrofit2.http.GET

interface IndexApi {
    @GET(Constant.INDEX_API)
    suspend fun getIndex(): Response<IndexResponse>

    @GET(Constant.PRODUCT_API)
    suspend fun getProduct(): Response<ProductResponse>

    @GET(Constant.CART_API)
    suspend fun getCart(): Response<CartResponse>
}