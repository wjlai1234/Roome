package com.example.roome.hotel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// binding adapter for setting url/uri on ImageView
@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}