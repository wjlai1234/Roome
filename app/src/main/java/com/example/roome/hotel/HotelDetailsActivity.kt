package com.example.roome.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.roome.R
import com.example.roome.databinding.ActivityHotelDetailsBinding
import com.example.roome.databinding.SplashActivityBinding

class HotelDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHotelDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_hotel_details
        )

        var hotel : Hotel? = intent.getParcelableExtra<Hotel>("object")
        binding.hotelDetails = hotel
    }
}