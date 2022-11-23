package com.example.f32.util

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

/****************************************************************************************************
 ** Toast
 ****************************************************************************************************/
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun AppCompatActivity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}

fun ImageView.load(imageAddress: String) {
    Glide.with(this)
        .load(imageAddress)
        .into(this)
}

/****************************************************************************************************
 **  Visibility
 ****************************************************************************************************/
fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}