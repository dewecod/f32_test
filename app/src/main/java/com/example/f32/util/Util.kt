package com.example.f32.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.f32.R
import com.example.f32.model.Category

class Util {
    companion object {
        val categoryList: List<Category> = listOf(
            Category(R.drawable.ic_category_phones, "Phones", true),
            Category(R.drawable.ic_category_computer, "Computer", false),
            Category(R.drawable.ic_category_health, "Health", false),
            Category(R.drawable.ic_category_books, "Books", false),
            Category(R.drawable.ic_category_books, "Others", false),
        )

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.NORMAL) // .format(DecodeFormat.PREFER_RGB_565)
        fun getRequestBuilder(context: Context): RequestBuilder<Drawable> = Glide.with(context).asDrawable().sizeMultiplier(0.2f)
    }
}