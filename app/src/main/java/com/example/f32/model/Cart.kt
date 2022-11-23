package com.example.f32.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "cart", primaryKeys = ["id"])
data class Cart(
    @Json(name = "id") @ColumnInfo(name = "id") val id: Int,

    @Json(name = "title") @ColumnInfo(name = "title") val title: String,
    @Json(name = "subtitle") @ColumnInfo(name = "subtitle") val subtitle: String,
    @Json(name = "picture") @ColumnInfo(name = "picture") val picture: String,

    @Json(name = "is_buy") @ColumnInfo(name = "is_buy") val isBuy: Boolean,
    @Json(name = "is_new") @ColumnInfo(name = "is_new") val isNew: Boolean = false,
) : Parcelable {}