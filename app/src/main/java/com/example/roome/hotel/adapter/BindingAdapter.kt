package com.example.roome.hotel

import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide

// binding adapter for setting url/uri on ImageView
@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("android:text")
fun setText(view: SearchView, text: MutableLiveData<String?>) {
    view.queryHint= text.toString()
}