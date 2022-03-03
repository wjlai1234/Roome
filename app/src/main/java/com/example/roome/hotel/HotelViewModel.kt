package com.example.roome.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HotelViewModel : ViewModel() {
//    private var _hotel  =  MutableLiveData<ArrayList<Hotel>>(allHotelsList) //allow editable in VM,
//    val hotel: LiveData<ArrayList<Hotel>>
//        get() = _hotel

    private var _startDate = MutableLiveData<Int>()
    val startDate: LiveData<Int>
        get() = _startDate

    private var _endDate = MutableLiveData<Int>()
    val endDate: LiveData<Int>
        get() = _endDate
}