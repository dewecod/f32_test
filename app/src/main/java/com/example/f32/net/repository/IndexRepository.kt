package com.example.f32.net.repository

import com.example.f32.di.dao.CartDao
import com.example.f32.net.api.IndexApi
import com.example.f32.net.response.IndexResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IndexRepository constructor(
    private val indexApi: IndexApi,
    private val cartDao: CartDao
) {
    suspend fun loadIndex(): Response<IndexResponse> = withContext(Dispatchers.IO) { indexApi.getIndex() }

    // suspend fun loadCartList() = withContext(Dispatchers.IO) { cartDao.getList() }
    suspend fun loadProduct() = withContext(Dispatchers.IO) { indexApi.getProduct() }
    suspend fun loadCart() = withContext(Dispatchers.IO) { indexApi.getCart() }
}