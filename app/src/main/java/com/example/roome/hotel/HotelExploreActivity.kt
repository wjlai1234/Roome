package com.example.roome.hotel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roome.R
import com.example.roome.databinding.ActivityExploreHotelBinding
import com.example.roome.hotel.adapter.HotelSearchByResultAdapter
import com.example.roome.hotel.viewmodel.BookViewModel
import com.example.roome.hotel.viewmodel.HotelViewModel

class HotelExploreActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityExploreHotelBinding
    private val hotelViewModel by lazy { ViewModelProvider(this).get(HotelViewModel::class.java) }
    private val bookViewModel by lazy { ViewModelProvider(this).get(BookViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_explore_hotel,
        )

        binding.rvSearchByResult.apply {
            layoutManager =
                LinearLayoutManager(this@HotelExploreActivity, LinearLayoutManager.VERTICAL, false)
            adapter = HotelSearchByResultAdapter(allHotelsList)
        }

    }

}