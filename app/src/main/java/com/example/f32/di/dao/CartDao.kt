package com.example.f32.di.dao

import androidx.room.*
import com.example.f32.model.Cart

@Dao
interface CartDao {
    @Query("SELECT * FROM cart WHERE id = :product_id")
    suspend fun getById(product_id: Int): List<Cart>

    @Query("SELECT * FROM cart")
    suspend fun getList(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: Cart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(brandList: List<Cart>)

    @Update
    suspend fun update(cart: Cart)

    @Delete
    suspend fun delete(cart: Cart)

    @Query("DELETE FROM cart")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAndCreate(cartList: List<Cart>) {
        deleteAll()
        insertList(cartList)
    }

    suspend fun insertOrUpdate(cart: Cart) {
        val product = getById(cart.id)
        if (product.isEmpty()) insert(cart)
        else update(cart)
    }
}