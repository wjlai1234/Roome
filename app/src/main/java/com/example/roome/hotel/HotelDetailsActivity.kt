package com.example.roome.hotel

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.TripsFragment
import com.example.roome.databinding.ActivityHotelDetailsBinding
import com.example.roome.databinding.SplashActivityBinding
import com.example.roome.hotel.viewmodel.HotelViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HotelDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHotelDetailsBinding
    private lateinit var auth: FirebaseAuth
    private val hotelViewModel by lazy { ViewModelProvider(this).get(HotelViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_hotel_details
        )

        var hotel : Hotel? = intent.getParcelableExtra<Hotel>("object")
        binding.apply {
            hotelDetails = hotel
            ivBack.setOnClickListener { onBackPressed() }
            btnBook.setOnClickListener{
                auth = Firebase.auth
                if (auth.currentUser != null) {
                    hotelViewModel.saveBestDealsHotel(hotel,this@HotelDetailsActivity)
                    finish()
                }else {
                Toast.makeText(this@HotelDetailsActivity,"Pls Log in", Toast.LENGTH_SHORT).show()
                }


            }
        }

    }
}