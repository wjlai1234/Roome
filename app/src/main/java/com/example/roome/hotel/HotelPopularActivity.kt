package com.example.roome.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roome.R
import com.example.roome.databinding.ActivityPopularBinding
import com.example.roome.hotel.viewmodel.HotelViewModel

class HotelPopularActivity : AppCompatActivity() {
    private val hotelViewModel by lazy { ViewModelProvider(this).get(HotelViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityPopularBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_popular
        )
        var popularArea : PopularArea? = intent.getParcelableExtra<PopularArea>("object")
             binding.popularData =popularArea
        if (popularArea != null) {
            hotelViewModel.loadPopular(binding,this,popularArea)
            binding.ivBack.setOnClickListener { onBackPressed() }
        }
    }
}