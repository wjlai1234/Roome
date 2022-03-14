package com.example.roome.hotel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.navArgs
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
    private var arrayBook: Book? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_explore_hotel,
        )
        val args: HotelExploreActivityArgs by navArgs()
        binding.apply {
            searchResult = args.searchData
            arrayBook  = args.searchData
            tiSearch.setQuery(arrayBook?.searchText, false)
            ivBack.setOnClickListener{
                onNavigateUp()
                onBackPressed()
            }

        }

        hotelViewModel.load(binding,this,arrayBook)
        hotelViewModel.search(binding, this)

    }

}