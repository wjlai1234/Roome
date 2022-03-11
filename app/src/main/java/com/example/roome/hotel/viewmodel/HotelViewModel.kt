package com.example.roome.hotel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roome.hotel.Hotel
import com.example.roome.hotel.allHotelsList

class HotelViewModel : ViewModel() {
    private var _hotel  =  MutableLiveData<ArrayList<Hotel>>(allHotelsList) //allow editable in VM,
    val hotel: LiveData<ArrayList<Hotel>> //
        get() = _hotel




}